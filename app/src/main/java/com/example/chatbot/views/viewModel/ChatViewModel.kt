package com.example.chatbot.views.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbot.data.ApiService
import com.example.chatbot.data.Message
import com.example.chatbot.data.OpenAIRequestBody
import kotlinx.coroutines.launch


class ChatViewModel : ViewModel() {

    var messages = mutableStateListOf<Message>()
    val errorState = mutableStateOf<String?>(null)

    fun sendMessage(text: String) {
        val userMessage = Message(text, "user")
        messages.add(userMessage)

        viewModelScope.launch {
            try {
                val response = ApiService.openAIApi.generateResponse(OpenAIRequestBody(messages = messages))
                val aiResponseMessage = response.choices.map { it.message }.first()
                messages.add(aiResponseMessage)
            } catch (e: Exception) {
                errorState.value = "Error: ${e.message}"
                Log.e("test", "${messages.toList()}")
            }
        }
    }

    fun hideErrorState() {
        errorState.value = null
    }

    fun clearMessages() {
        messages.clear()
    }
}