package com.example.chatbot.views.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbot.data.ApiService
import com.example.chatbot.data.OpenAIRequestBody
import com.example.chatbot.model.Message
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.pow


class ChatViewModel: ViewModel() {

    val messages = mutableStateListOf<Message>()

    fun sendMessage(text: String, isUser: Boolean = true) {
        if (isUser) {
            messages.add(Message(text, "user"))
            viewModelScope.launch {
                try {
                    val response = ApiService.openAIApi.generateResponse(OpenAIRequestBody(messages = messages))
                    val botMessage = response.choices.first().message
                    messages.add(botMessage)
                } catch (e: HttpException) {
                    Log.e("API_ERROR", "HTTP error: ${e.message}")
                } catch (e: IOException) {
                    Log.e("API_ERROR", "Network error: ${e.message}")
                } catch (e: Throwable) {
                    Log.e("API_ERROR", "Unknown error: ${e.message}")
                }
            }
        }
    }
}