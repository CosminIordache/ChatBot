package com.example.chatbot.data

import com.example.chatbot.model.Message
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenAIApi {
    @Headers("Content-Type: application/json", "Authorization: Bearer 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")
    @POST("v1/chat/completions")
    suspend fun generateResponse(@Body requestBody: OpenAIRequestBody): OpenAIResponse
}

data class OpenAIRequestBody(
    val model: String = "gpt-3.5-turbo-0301",
    val messages: List<Message>,
    val max_tokens: Int = 100,
    val n: Int = 1,
    val temperature: Double = 1.0
)

data class OpenAIResponse(
    val choices: List<MessageResponse>
)

data class MessageResponse(
    val message: Message
)