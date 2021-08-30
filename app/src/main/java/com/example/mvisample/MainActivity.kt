package com.example.mvisample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mvisample.ui.theme.MVISampleTheme
import com.example.mvisample.view.TaskListComponent
import com.example.mvisample.viewModel.TaskListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope


@AndroidEntryPoint
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
fun MainContainer() {
    val navController = rememberNavController()
    Scaffold {
        NavHost(navController, startDestination = "tasklist") {
            composable("tasklist") {
                val taskListComponentViewModel: TaskListViewModel = viewModel()
                val taskListState by taskListComponentViewModel.uiState.observeAsState()
                TaskListComponent(taskListState)
            }
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