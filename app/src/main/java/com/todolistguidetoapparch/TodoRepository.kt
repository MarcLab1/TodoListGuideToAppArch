package com.todolistguidetoapparch

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface TodoRepository {

    fun getTodos() : Flow<List<Todo>>
    suspend fun insertTodo(todo: Todo) : Unit
    suspend fun updateTodo(todo: Todo) : Unit
    suspend fun deleteTodo(todo: Todo) : Unit
}

class ProdTodoRepository @Inject constructor(
    private val db : TodoDatabase
): TodoRepository
{
    override fun getTodos(): Flow<List<Todo>> {
        return db.todoDao().getTodos()
    }

    override suspend fun insertTodo(todo: Todo) {
        return db.todoDao().insertTodo(todo)
    }

    override suspend fun updateTodo(todo: Todo) {
       return db.todoDao().updateTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        return db.todoDao().deleteTodo(todo)
    }
}