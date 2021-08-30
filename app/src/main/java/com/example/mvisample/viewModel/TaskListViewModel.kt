package com.example.mvisample.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvisample.Application
import com.example.mvisample.view.state.TaskListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(): ViewModel() {
    val uiState: MutableLiveData<TaskListState> = MutableLiveData(TaskListState.Loading)

    fun fetchTaskList() {
        try {
            val values = Application.database?.getTaskDao()?.getAll() ?: throw Exception()
            uiState.postValue(TaskListState.Success(values))
        }catch (e: Exception) {
            uiState.postValue(TaskListState.Error(e))
        }
    }
}