package com.example.mvisample.view.state

import com.example.mvisample.model.Task
import java.lang.Exception

sealed class TaskListState {
    data class Success(val tasks: List<Task>): TaskListState()
    data class Error(val exception: Exception): TaskListState()
    object Loading : TaskListState()
}
