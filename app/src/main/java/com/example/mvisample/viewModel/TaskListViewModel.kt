package com.example.mvisample.viewModel

import androidx.lifecycle.ViewModel
import com.example.mvisample.Database
import com.example.mvisample.model.Task
import com.example.mvisample.view.state.TaskListState

class TaskListViewModel(private val database: Database): ViewModel() {
    var uiState: TaskListState = TaskListState.Loading

    fun fetchTaskList() {
        uiState = try {
            val values = database.getTaskDao().getAll()
            TaskListState.Success(values)
        }catch (e: Exception) {
            TaskListState.Error(e)
        }
    }
}