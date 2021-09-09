package com.example.mvisample.model.state

import com.example.mvisample.model.Task

sealed class NewTaskState {
    object Loading: NewTaskState()
    data class Success(val task: Task?): NewTaskState()
    data class Error(val e: Exception): NewTaskState()
}
