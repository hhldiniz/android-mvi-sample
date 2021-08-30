package com.example.mvisample.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class TaskItem(
    @PrimaryKey val uuid: String = UUID.randomUUID().toString(),
    var description: String, var done: Boolean
)
