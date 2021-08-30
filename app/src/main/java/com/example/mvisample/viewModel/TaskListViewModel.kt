package com.example.mvisample.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvisample.view.state.TaskListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(): ViewModel() {
    var uiState: LiveData<TaskListState> = MutableLiveData(TaskListState.Loading)
}