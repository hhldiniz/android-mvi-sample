package com.example.mvisample.view

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.example.mvisample.viewModel.NewTaskViewModel
import org.koin.androidx.compose.get

@Composable
fun NewTask(newTaskViewModel: NewTaskViewModel = get()) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {}){
            Icon(Icons.Filled.Create, "Add new Task", tint = Color.White)
        }
    }) {
        Column {
            Card(modifier = Modifier.padding(Dp(8f))) {
                TextField(
                    "",
                    label = {
                        Text("Task name")
                    }, onValueChange = { newValue ->
                        Log.d("textValue", newValue)
                    }, modifier = Modifier.fillMaxWidth().then(Modifier.padding(Dp(8f))))
            }
            Card {
                Row {

                }
            }
        }
    }
}