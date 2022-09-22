package com.todolistguidetoapparch

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Todo")
data class Todo(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name", index = true) val name: String = "",
    @ColumnInfo(name = "completed") val completed: Boolean = false,
)

data class TodoUI(val id: Int, val name: String, val completed: Boolean, val onChangeCompleted:() -> Unit = {}, val onDelete:() -> Unit = {})

fun TodoUI.toTodo() : Todo {
    return Todo(this.id, this.name, this.completed)
}

