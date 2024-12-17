package com.example

import com.example.data.model.ToDoDraft
import com.example.data.repository.MySqlToDoRepository
import com.example.data.repository.ToDoRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val repository: ToDoRepository = MySqlToDoRepository()
    configureSerialization()
    configureMonitoring()
    configureRouting()

    routing {
        route("/todos"){
            get {
                val todos = repository.getAllToDos()

                call.respond(todos)

            }

            get("/{id}"){
                val id = call.parameters["id"]?.toIntOrNull()

                if(id == null){
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
                val task = repository.getToDo(id)

                if(task == null){
                    call.respond(HttpStatusCode.NotFound)
                    return@get
                }
                call.respond(task)
            }

            post {
                try{
                    val toDoDraft = call.receive<ToDoDraft>()
                    repository.addToDo(toDoDraft)

                    call.respond(HttpStatusCode.Created)
                } catch (e: IllegalStateException){
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            delete("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if(id == null){
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                val isDeleted = repository.deleteToDo(id)
                if(isDeleted){
                    call.respond(HttpStatusCode.NoContent)
                }else{
                    call.respond(HttpStatusCode.NotFound)
                }
            }

            put("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if(id == null){
                    call.respond(HttpStatusCode.BadRequest)
                    return@put
                }
                val toDoDraft = call.receive<ToDoDraft>()

                val isUpdated = repository.updateToDo(id,toDoDraft)

                if (isUpdated){
                    call.respond(HttpStatusCode.OK)
                }else{
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }
    }
}


