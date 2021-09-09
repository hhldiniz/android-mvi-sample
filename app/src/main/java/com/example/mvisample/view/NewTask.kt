package com.example.mvisample.view

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.example.mvisample.model.state.NewTaskState
import com.example.mvisample.view.intent.NewTaskIntent
import com.example.mvisample.viewModel.NewTaskViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@Composable
fun NewTask(newTaskViewModel: NewTaskViewModel = get()) {
    val newTaskState by remember { mutableStateOf(newTaskViewModel.state) }
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
                            }, modifier = Modifier.fillMaxWidth().then(Modifier.padding(Dp(8f)))
                        )
                    }
                    Card {
                        (newTaskState as NewTaskState.Success).task?.items?.let { items ->
                            repeat(items.count()) { index ->
                                Row {
                                    Checkbox(items[index].done, { checked ->

                                    })
                                }
                            }
                            return@let
                        }
                        Text("No Items")
                    }
                }
                is NewTaskState.Error -> Text("Error loading data")
            }
        }.also {
            coroutineScope.launch(Dispatchers.Default) {
                newTaskViewModel.newTaskIntent.send(NewTaskIntent.FetchTaskDetails(null))
            }
        }
    }
}