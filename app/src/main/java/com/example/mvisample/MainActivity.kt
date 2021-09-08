package com.example.mvisample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mvisample.ui.theme.MVISampleTheme
import com.example.mvisample.view.NewTask
import com.example.mvisample.view.TaskListComponent
import com.example.mvisample.view.intent.TaskListIntent
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
                rememberCoroutineScope { coroutineContext }
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
    val coroutineScope = rememberCoroutineScope()
    NavHost(navController, startDestination = "tasklist") {
        composable("tasklist") {
            val taskListState by remember { mutableStateOf(taskListComponentViewModel.state) }
            TaskListComponent(taskListState, navController)
        }.also {
            coroutineScope.launch(Dispatchers.Default) {
                taskListComponentViewModel.taskListIntent.send(TaskListIntent.FetchTaskList)
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