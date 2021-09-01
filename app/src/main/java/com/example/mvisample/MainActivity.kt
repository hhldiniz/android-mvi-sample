package com.example.mvisample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mvisample.ui.theme.MVISampleTheme
import com.example.mvisample.view.NewTask
import com.example.mvisample.view.TaskListComponent
import com.example.mvisample.viewModel.TaskListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get


class MainActivity : ComponentActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVISampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainContainer()
                }
            }
        }
    }
}

@Composable
fun MainContainer(taskListComponentViewModel: TaskListViewModel = get()) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "tasklist") {
        composable("tasklist") {
            val taskListState by remember { mutableStateOf(taskListComponentViewModel.uiState) }
            TaskListComponent(taskListState, navController)
        }.also {
            MainScope().launch(Dispatchers.Default) {
                taskListComponentViewModel.fetchTaskList()
            }
        }
        composable("newtask") {
            NewTask()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MVISampleTheme {
        MainContainer()
    }
}