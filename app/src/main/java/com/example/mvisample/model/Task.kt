package com.example.mvisample.model

import androidx.room.Entity

@Entity
data class Task(var name: String, var items: List<TaskItem>)
