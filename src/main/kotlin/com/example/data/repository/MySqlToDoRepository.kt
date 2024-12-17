package com.example.data.repository

import com.example.data.database.DatabaseManager
import com.example.data.model.ToDo
import com.example.data.model.ToDoDraft

class MySqlToDoRepository: ToDoRepository {

    private val db = DatabaseManager()

    override suspend fun getAllToDos(): List<ToDo> {
        return db.getAllTodos().map {
            ToDo(
                id = it.id,
                title = it.title,
                done = it.done
            )
        }
    }

    override suspend fun getToDo(id: Int): ToDo? {
        return db.getTodo(id)?.let {
            ToDo(
                id = it.id,
                title = it.title,
                done = it.done
            )
        }
    }

    override suspend fun addToDo(toDoDraft: ToDoDraft) {
        return db.addTodo(toDoDraft)
    }

    override suspend fun deleteToDo(id: Int): Boolean {
        return db.deleteTodo(id)
    }

    override suspend fun updateToDo(id: Int, toDoDraft: ToDoDraft): Boolean {
        return db.updateTodo(id, toDoDraft)
    }
}