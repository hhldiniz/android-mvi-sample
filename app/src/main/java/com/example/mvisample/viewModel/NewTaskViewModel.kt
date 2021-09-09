package com.example.mvisample.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvisample.Database
import com.example.mvisample.model.Task
import com.example.mvisample.model.TaskItem
import com.example.mvisample.model.state.NewTaskState
import com.example.mvisample.view.intent.NewTaskIntent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class NewTaskViewModel(private val database: Database) : ViewModel() {
    val newTaskIntent = Channel<NewTaskIntent> { Channel.UNLIMITED }
    var state: NewTaskState = NewTaskState.Loading

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch(Dispatchers.Default) {
            newTaskIntent.consumeAsFlow().collect {
                when (it) {
                    is NewTaskIntent.FetchTaskDetails -> fetchTaskDetails(it.uuid)
                    is NewTaskIntent.AddTaskItem -> addTaskItem(
                        it.uuid,
                        it.description,
                        it.taskDescription
                    )
                    is NewTaskIntent.AddTask -> addTask(it.description)
                }
            }
        }
    }

    private fun fetchTaskDetails(uuid: String?) {
        state = NewTaskState.Loading
        viewModelScope.launch(Dispatchers.Default) {
            state = try {
                NewTaskState.Success(database.getTaskDao().getTaskById(uuid.orEmpty()))
            } catch (e: Exception) {
                NewTaskState.Error(e)
            }
        }
    }

    private fun addTask(description: String) {
        state = NewTaskState.Loading
        viewModelScope.launch(Dispatchers.Default) {
            state = try {
                val newTask = Task(name = description, items = mutableListOf())
                with(database.getTaskDao()) {
                    insert(newTask)
                    return@with NewTaskState.Success(newTask)
                }
            } catch (e: Exception) {
                NewTaskState.Error(e)
            }
        }
    }

    private fun addTaskItem(uuid: String?, description: String, taskDescription: String) {
        state = NewTaskState.Loading
        viewModelScope.launch(Dispatchers.Default) {
            var task: Task?
            state = try {
                with(database.getTaskDao()) {
                    getTaskById(uuid.orEmpty())?.let { currentTask ->
                        task = currentTask
                        currentTask.items.add(
                            TaskItem(currentTask.uuid, description, false)
                        )
                        update(currentTask)
                        return@with NewTaskState.Success(task)
                    }
                    TaskItem(description = description, done = false).apply {
                        task = Task(this.uuid, taskDescription, mutableListOf(this@apply))
                    }
                    return@with NewTaskState.Success(task)
                }
            } catch (e: Exception) {
                NewTaskState.Error(e)
            }
        }
    }
}