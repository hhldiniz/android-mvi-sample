package com.example.mvisample.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.mvisample.view.state.TaskListState

@Composable
fun TaskListComponent(taskListState: TaskListState?, navController: NavController) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate("newtask")
        }){
            Icon(Icons.Filled.Add, "Add", tint = Color.White)
        }
    }) {
        Column {
            when (taskListState) {
                is TaskListState.Loading -> Text("Loading ...")
                is TaskListState.Success -> {
                    if(taskListState.tasks.isEmpty())
                        Text("No data")
                    else
                        repeat(taskListState.tasks.count()) { index ->
                            Text(taskListState.tasks[index].name)
                        }
                }
                is TaskListState.Error -> Text("Error while fetching data")
            }
        }
    }
}