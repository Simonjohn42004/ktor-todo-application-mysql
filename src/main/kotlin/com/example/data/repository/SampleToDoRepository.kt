package com.example.data.repository

import com.example.data.model.ToDo
import com.example.data.model.ToDoDraft

class SampleToDoRepository: ToDoRepository {

    private var toDos = mutableListOf(
        ToDo(
            id = 1,
            title = "Buy groceries",
            done = false
        ),
        ToDo(
            id = 2,
            title = "Complete project report",
            done = true
        ),
        ToDo(
            id = 3,
            title = "Call the plumber",
            done = false
        ),
        ToDo(
            id = 4,
            title = "Schedule dentist appointment",
            done = true
        ),
        ToDo(
            id = 5,
            title = "Finish reading book",
            done = false
        )
    )

    override suspend fun getAllToDos(): List<ToDo> {
        return toDos
    }

    override suspend fun getToDo(id: Int): ToDo? {
        return toDos.firstOrNull { it.id == id }
    }

    override suspend fun addToDo(toDoDraft: ToDoDraft) {
        if(toDos.any { it.title.equals(toDoDraft.title, true) }){
            throw IllegalStateException("No duplicate To-Do can be allowed")
        }
        toDos.add(
            ToDo(
                id = toDos.size + 1,
                title = toDoDraft.title,
                done = toDoDraft.done
            )
        )
    }

    override suspend fun deleteToDo(id: Int): Boolean {
        return toDos.removeIf {
            it.id == id
        }
    }

    override suspend fun updateToDo(id: Int, toDoDraft: ToDoDraft): Boolean {
        val todo = toDos.firstOrNull { it.id == id} ?: return false
        todo.title = toDoDraft.title
        todo.done = toDoDraft.done

        return true
    }

}