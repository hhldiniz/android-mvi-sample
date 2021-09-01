package com.example.mvisample.view

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable

@Composable
fun NewTask() {
    Column {
        Card {
            TextField(
                "",
                label = {
                    Text("Task name")
                }, onValueChange = { newValue ->
                    Log.d("textValue", newValue)
                })
        }
        Card {
            Row {

            }
        }
    }
}