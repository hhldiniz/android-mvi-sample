package com.example.mvisample.model

import androidx.room.Entity

@Entity
data class TaskItem(var description: String, var done: Boolean)
