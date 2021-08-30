package com.example.mvisample.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.mvisample.view.state.TaskListState

@Composable
fun TaskListComponent(taskListState: TaskListState?) {
    Column {
        when (taskListState) {
            is TaskListState.Loading -> Text("Loading ...")
            is TaskListState.Success -> repeat(taskListState.tasks.count()) { index ->
                Text(taskListState.tasks[index].name)
            }
            is TaskListState.Error -> Text("Error while fetching data")
        }
    }
}