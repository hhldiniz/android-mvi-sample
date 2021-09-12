package com.example.mvisample.view

import android.util.Log
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
                    Card(modifier = Modifier.padding(Dp(8f))) {
                        TextField(
                            "",
                            label = {
                                Text("Task name")
                            }, onValueChange = { newValue ->
                                Log.d("textValue", newValue)
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
                                        Row {
                                            Checkbox(items[index].done, { checked ->
                                                coroutineScope.launch(Dispatchers.Default) {
                                                    newTaskViewModel.newTaskIntent.send(
                                                        NewTaskIntent.UpdateTaskItem(
                                                            items[index].apply {
                                                                done = checked
                                                            })
                                                    )
                                                }
                                            })
                                            TextField(
                                                value = items[index].description,
                                                onValueChange = { newDescription ->
                                                    coroutineScope.launch(Dispatchers.Default) {
                                                        newTaskViewModel.newTaskIntent.send(
                                                            NewTaskIntent.UpdateTaskItem(
                                                                items[index].apply {
                                                                    description = newDescription
                                                                })
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