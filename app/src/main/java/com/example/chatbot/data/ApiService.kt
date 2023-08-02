package com.example.chatbot.data

import com.example.chatbot.model.Util
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    fun create(): ChatGptApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(Util.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ChatGptApi::class.java)
    }
}