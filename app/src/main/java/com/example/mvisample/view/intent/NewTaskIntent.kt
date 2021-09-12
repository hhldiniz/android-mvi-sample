package com.example.mvisample.view.intent

import com.example.mvisample.model.TaskItem

sealed class NewTaskIntent {
    data class FetchTaskDetails(val uuid: String?) : NewTaskIntent()
    data class AddTaskItem(
        val uuid: String?,
        val description: String,
        val taskDescription: String
    ) : NewTaskIntent()
    data class AddTask(val description: String): NewTaskIntent()
    data class UpdateTaskItem(val taskItem: TaskItem): NewTaskIntent()
}