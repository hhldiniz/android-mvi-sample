package com.example.mvisample.model.dao

import androidx.room.*
import com.example.mvisample.model.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM Task")
    fun getAll(): List<Task>

    @Insert
    fun insert(vararg tasks: Task)

    @Update
    fun update(vararg tasks: Task): Int

    @Delete
    fun delete(vararg tasks: Task)
}