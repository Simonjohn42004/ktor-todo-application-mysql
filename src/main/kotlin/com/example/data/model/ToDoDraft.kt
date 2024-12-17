package com.example.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ToDoDraft(
    val title: String,
    val done: Boolean
)