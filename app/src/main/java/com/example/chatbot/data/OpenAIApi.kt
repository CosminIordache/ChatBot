package com.example.chatbot.data

import com.example.chatbot.model.Message
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ChatGptApi {
    @Headers("Content-Type: application/json", "Authorization: Bearer API_KEY")
    @POST("v1/chat/completions")
    suspend fun generateResponse(@Body requestBody: OpenAIRequestBody): OpenAIResponse
}

data class OpenAIRequestBody(
    val model: String = "gpt-3.5-turbo-0613",
    val messages: List<Message>
)

data class OpenAIResponse(
    val id: String,
    val object_: String,
    val choices: List<Choice>
)

data class Choice(
    val message: String,
    val finish_reason: String,
    val index: Int
)
