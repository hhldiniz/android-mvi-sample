package com.example.mvisample.view.intent

sealed class NewTaskIntent {
    data class FetchTaskDetails(val uuid: String?) : NewTaskIntent()
    data class AddTaskItem(
        val uuid: String?,
        val description: String,
        val taskDescription: String
    ) : NewTaskIntent()
    data class AddTask(val description: String): NewTaskIntent()
}