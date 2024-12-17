package com.example.data.database

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object ToDoTable: Table<ToDoDao>("todo") {
    val id = int("id").primaryKey().bindTo { it.id }
    val title = varchar("title").bindTo { it.title }
    val done = boolean("done").bindTo { it.done }
}

interface ToDoDao: Entity<ToDoDao> {
    companion object: Entity.Factory<ToDoDao>()

    val id: Int
    val title: String
    val done: Boolean

}




