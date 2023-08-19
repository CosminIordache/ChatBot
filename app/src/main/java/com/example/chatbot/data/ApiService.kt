package com.example.chatbot.data

import com.example.chatbot.model.Util
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openai.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val openAIApi: ChatGptApi = retrofit.create(ChatGptApi::class.java)
}