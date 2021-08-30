package com.example.mvisample.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Task(
    @PrimaryKey val uuid: String = UUID.randomUUID().toString(),
    var name: String,
    var items: MutableList<TaskItem>
    )
