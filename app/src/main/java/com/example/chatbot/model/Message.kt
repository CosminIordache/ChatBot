package com.example.chatbot.model

data class Message(val content: String, val role: String) {
    val isUser: Boolean
        get() = role == "user"
}