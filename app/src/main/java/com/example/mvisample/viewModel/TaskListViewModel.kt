package com.example.mvisample.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvisample.Database
import com.example.mvisample.model.state.TaskListState
import com.example.mvisample.view.intent.TaskListIntent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class TaskListViewModel(private val database: Database) : ViewModel() {
    val taskListIntent = Channel<TaskListIntent> { Channel.UNLIMITED }
    var state: TaskListState = TaskListState.Loading

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch(Dispatchers.Default) {
            taskListIntent.consumeAsFlow().collect {
                when (it) {
                    is TaskListIntent.FetchTaskList -> fetchTaskList()
                }
            }
        }
    }

    private fun fetchTaskList() {
        viewModelScope.launch(Dispatchers.Default) {
            state = TaskListState.Loading
            state =
                try {
                    TaskListState.Success(database.getTaskDao().getAll())
                } catch (e: Exception) {
                    TaskListState.Error(e)
                }
        }
    }
}