package com.example.chatbot.model

data class Message(
    val content: String,
    val role: String
    ){
    companion object{
        const val USER_SEND = "user_send"
        const val BOT_SEND = "bot_send"
    }
}