package com.example.mvisample.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.mvisample.model.Task
import com.example.mvisample.model.state.NewTaskState
import com.example.mvisample.view.intent.NewTaskIntent
import com.example.mvisample.viewModel.NewTaskViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@Composable
fun NewTask(newTaskState: NewTaskState, task: Task?, newTaskViewModel: NewTaskViewModel = get()) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {}) {
            Icon(Icons.Filled.Create, "Add new Task", tint = Color.White)
        }
    }) {
        Column {
            when (newTaskState) {
                is NewTaskState.Loading -> Text("Loading ...")
                is NewTaskState.Success -> {
                    var taskDescriptionState by remember {
                        mutableStateOf(newTaskState.task?.name)
                    }
                    Card(modifier = Modifier.padding(Dp(8f))) {
                        TextField(
                            taskDescriptionState.orEmpty(),
                            label = {
                                Text("Task name")
                            }, onValueChange = { newValue ->
                                taskDescriptionState = newValue
                                newTaskState.task?.apply {
                                    name = newValue
                                    coroutineScope.launch(Dispatchers.Default) {
                                        newTaskViewModel.newTaskIntent.send(
                                            NewTaskIntent.UpdateTask(
                                                this@apply
                                            )
                                        )
                                    }
                                    return@apply
                                }
                                coroutineScope.launch(Dispatchers.Default) {
                                    newTaskViewModel.newTaskIntent.send(
                                        NewTaskIntent.AddTask(
                                            taskDescriptionState.orEmpty()
                                        )
                                    )
                                }
                            }, modifier = Modifier
                                .fillMaxWidth()
                                .then(Modifier.padding(Dp(8f)))
                        )
                    }
                    Card {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                newTaskState.task?.items?.let { items ->
                                    repeat(items.count()) { index ->
                                        var itemState by remember {
                                            mutableStateOf(items[index])
                                        }
                                        Row {
                                            Checkbox(itemState.done, { checked ->
                                                coroutineScope.launch(Dispatchers.Default) {
                                                    newTaskViewModel.newTaskIntent.send(
                                                        NewTaskIntent.UpdateTaskItem(
                                                            itemState.apply {
                                                                done = checked
                                                            }.also {
                                                                itemState = it
                                                            }
                                                        )
                                                    )
                                                }
                                            })
                                            TextField(
                                                value = itemState.description,
                                                onValueChange = { newDescription ->
                                                    coroutineScope.launch(Dispatchers.Default) {
                                                        newTaskViewModel.newTaskIntent.send(
                                                            NewTaskIntent.UpdateTaskItem(
                                                                itemState.apply {
                                                                    description = newDescription
                                                                }.also {
                                                                    itemState = it
                                                                }
                                                            )
                                                        )
                                                    }
                                                }
                                            )
                                        }
                                    }
                                    return@let
                                }
                            }
                            Column {
                                IconButton(onClick = {
                                    coroutineScope.launch(Dispatchers.Default) {
                                        newTaskViewModel.newTaskIntent.send(
                                            NewTaskIntent.AddTaskItem(
                                                task?.uuid,
                                                task?.name.orEmpty(),
                                                ""
                                            )
                                        )
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.AddCircle,
                                        contentDescription = "Add Task Item"
                                    )
                                }
                            }
                        }
                    }
                }
                is NewTaskState.Error -> Text("Error loading data")
            }
        }
    }
}

@Preview
@Composable
fun NewTaskPreview() {
    NewTask(NewTaskState.Loading, null)
}