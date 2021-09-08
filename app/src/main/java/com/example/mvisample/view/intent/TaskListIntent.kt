package com.example.mvisample.view.intent

sealed class TaskListIntent {
    object FetchTaskList: TaskListIntent()
}
