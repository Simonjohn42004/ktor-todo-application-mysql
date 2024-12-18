package com.example

import com.example.data.model.ToDo
import com.example.data.model.ToDoDraft
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {
        application {
            module()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }

    @Test
    fun getAllTodos() = testApplication {
        application {
            module()
        }

        val response = client.get("/todos")
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun getTodoByInvalidIdProduces400() = testApplication {
        application {
            module()
        }


        val response = client.get("/todos/invalid")
        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun getTodoByIdTest() = testApplication {
        application {
            module()
        }

        val client = createClient {
            install(ContentNegotiation){
                json()
            }
        }

        val response = client.get("/todos/1")
        val actualResponseValue = response.body<ToDo>()
        val expectedResponseValue = ToDo(
            id = 1,
            title = "Buy groceries",
            done = false
        )
        assertEquals(expectedResponseValue, actualResponseValue)
    }

    @Test
    fun getTodoByIdNotPresentProduces404() = testApplication {
        application {
            module()
        }

        val response = client.get("/todos/10")
        assertEquals(HttpStatusCode.NotFound, response.status)
    }

    @Test
    fun todoCanAdded() = testApplication {
        application {
            module()
        }
        val client = createClient {
            install(ContentNegotiation){
                json()
            }
        }

        val toDoDraft = ToDoDraft(
            title = "Go to the Gym and workout",
            done = false
        )

        val response = client.post("/todos"){
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(toDoDraft)
        }

        assertEquals(HttpStatusCode.Created, response.status)

        val response2 = client.get("/todos")
        val actualBody = response2.body<List<ToDo>>()
        val todo = ToDo(
            id = 6,
            title = "Go to the Gym and workout",
            done = false
        )
        assertEquals(HttpStatusCode.OK, response2.status)
        assertContains(actualBody, todo )
    }

    @Test
    fun todoCanBeUpdate() = testApplication {
        application {
            module()
        }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val toDoDraft = ToDoDraft(
            title = "Finish reading book",
            done = true
        )
        val response = client.put("/todos/5") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(toDoDraft)
        }


        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun todoCanBeDeleted() = testApplication {
        application {
            module()
        }
        val client = createClient {
            install(ContentNegotiation){
                json()
            }
        }

        val response = client.delete("/todos/1")
        assertEquals(HttpStatusCode.NoContent, response.status)
    }

}
