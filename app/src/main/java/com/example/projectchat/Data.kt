package com.example.projectchat


import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class MyMessage(
    val usuario: String,
    val body: String,
    val hora: Int = Integer.parseInt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("mm")))
)

val messages: List<MyMessage> = listOf(
    MyMessage(
        "Remoto",
        "You can think of Modifiers as implementations of the decorator pattern",
    ),
    MyMessage(
        "Local",
        "Yeah, or you can think about anything else",
    ),
    MyMessage(
        "Remoto",
        "Nah",
    ),
    MyMessage(
        "Remoto",
        "Don't think so",
    ),
    MyMessage(
        "Local",
        "Yeah, sure",
    )
)