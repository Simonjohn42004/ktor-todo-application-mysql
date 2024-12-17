package com.example.data.database

import com.example.data.model.ToDoDraft
import org.ktorm.database.Database
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.insertAndGenerateKey
import org.ktorm.dsl.update
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList

class DatabaseManager {

    private val hostName = "localhost"
    private val dbName = "ktor_todo_db"
    private val username = "root"
    private val password = "Rubysankar@04"

    private val database: Database

    init {
        val jdbcUrl = "jdbc:mysql://$hostName:3306/$dbName"
        database = Database.connect(
            url = jdbcUrl,
            driver = "com.mysql.cj.jdbc.Driver",
            user = username,
            password = password
        )
    }

    fun getAllTodos(): List<ToDoDao>{
        return database.sequenceOf(ToDoTable).toList()
    }

    fun getTodo(id: Int): ToDoDao?{
        return database.sequenceOf(ToDoTable)
            .firstOrNull { it.id eq id }
    }

    fun addTodo(draft: ToDoDraft){
        database.insertAndGenerateKey(ToDoTable){
            set(ToDoTable.title,draft.title)
            set(ToDoTable.done, draft.done)
        }
    }

    fun deleteTodo(id: Int): Boolean{
        val deletedRow = database.delete(ToDoTable){
            it.id eq id
        }

        return deletedRow > 0
    }

    fun updateTodo(id: Int, toDoDraft: ToDoDraft): Boolean{
        val insertedRow = database.update(ToDoTable){
            set(it.title, toDoDraft.title)
            set(it.done, toDoDraft.done)
            where { it.id eq id }
        }

        return insertedRow > 0
    }
}