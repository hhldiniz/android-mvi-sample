package com.example.mvisample.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.Navigator
import com.example.mvisample.model.state.TaskListState

@Composable
fun TaskListComponent(taskListState: TaskListState?, navController: NavController) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate("newtask")
        }) {
            Icon(Icons.Filled.Add, "Add", tint = Color.White)
        }
    }) {
        Column {
            when (taskListState) {
                is TaskListState.Loading -> Text("Loading ...")
                is TaskListState.Success -> {
                    if (taskListState.tasks.isEmpty())
                        Text("No data")
                    else
                        repeat(taskListState.tasks.count()) { index ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .then(Modifier.padding(Dp(8f)))
                                    .then(Modifier.clickable {
                                        navController.navigate("newtask", )
                                    })
                            ) {
                                Text(taskListState.tasks[index].name)
                            }
                        }
                }
                is TaskListState.Error -> Text("Error while fetching data")
            }
        }
    }
}