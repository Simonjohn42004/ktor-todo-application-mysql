package com.example.data.repository

import com.example.data.model.ToDo
import com.example.data.model.ToDoDraft

interface ToDoRepository {

    suspend fun getAllToDos(): List<ToDo>

    suspend fun getToDo(id: Int): ToDo?

    suspend fun addToDo(toDoDraft: ToDoDraft)

    suspend fun deleteToDo(id: Int): Boolean

    suspend fun updateToDo(id: Int, toDoDraft: ToDoDraft): Boolean
}