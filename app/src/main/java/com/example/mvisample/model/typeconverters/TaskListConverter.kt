package com.example.mvisample.model.typeconverters

import androidx.room.TypeConverter
import com.example.mvisample.model.TaskItem
import org.json.JSONObject

object TaskListConverter {

    @TypeConverter
    fun fromMutableList(list: MutableList<TaskItem>): String {
        return list.joinToString {
            "{'uuid': '${it.uuid}', 'description': '${it.description}', " +
                    "'done': ${if (it.done) "true" else "false"}}"
        }
    }

    @TypeConverter
    fun toMutableList(stringRepresentation: String): MutableList<TaskItem> {
        val parsedList = mutableListOf<TaskItem>()
        stringRepresentation.split(",").forEach {
            JSONObject(it).apply {
                parsedList.add(
                    TaskItem(
                        getString("uuid"),
                        getString("description"),
                        getString("done") == "true"
                    )
                )
            }
        }
        return parsedList
    }
}