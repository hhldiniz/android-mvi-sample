package com.example.mvisample

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mvisample.model.Task
import com.example.mvisample.model.dao.TaskDao
import com.example.mvisample.model.typeconverters.TaskListConverter

@Database(
    entities = [
        Task::class
    ],
    version = 1
)
@TypeConverters(TaskListConverter::class)
abstract class Database: RoomDatabase() {
   abstract fun getTaskDao(): TaskDao
}