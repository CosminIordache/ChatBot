package com.example.chatbot.data

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ChatGptApi {
    @Headers("Content-Type: application/json", "Authorization: Bearer API_KEY")
    @POST("v1/chat/completions")
    suspend fun generateResponse(@Body requestBody: OpenAIRequestBody): OpenAIResponse
}

data class OpenAIRequestBody(
    val model: String = "gpt-3.5-turbo-16k-0613",
    val messages: List<Message>,
    val max_tokens: Int = 50,
    val n: Int = 1,
    val temperature: Double = 1.0
)

data class OpenAIResponse(
    val choices: List<MessageResponse>
)

data class Message(
    val content: String,
    val role: String
)

data class MessageResponse(
    val message: Message
)
