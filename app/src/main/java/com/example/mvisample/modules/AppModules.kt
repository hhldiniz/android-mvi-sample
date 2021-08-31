package com.example.mvisample.modules

import androidx.room.Room
import com.example.mvisample.Database
import com.example.mvisample.viewModel.TaskListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val globalModules = module {
    single {
        Room.databaseBuilder(
            get(),
            Database::class.java,
            "mvi-sample").build()
    }
}

private val taskListModules = module {
    viewModel { TaskListViewModel(get()) }
}

val modulesList = listOf(globalModules, taskListModules)