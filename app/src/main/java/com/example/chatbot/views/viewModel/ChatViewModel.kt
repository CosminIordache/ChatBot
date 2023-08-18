package com.example.chatbot.views.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbot.data.ApiService
import com.example.chatbot.data.OpenAIRequestBody
import com.example.chatbot.model.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ChatViewModel: ViewModel() {

    private val api = ApiService.create()
    private var _messages = mutableStateListOf<Message>()
    var messages: List<Message> = _messages

    private val _errorState = mutableStateOf<String?>(null)
    val errorState = _errorState

    init {
        _messages.addAll(listOf(
            Message("Hola", "user"),
            Message("Hola, ¿cómo puedo ayudarte?", "ai")
        ))
    }

    fun generateResponse(){
        val currentMessages = _messages.toList()
        val requestBody = OpenAIRequestBody(messages = currentMessages)

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO){
                    api.generateResponse(requestBody)
                }

                val replyMessage = response.choices.firstOrNull()?.message

                if (!replyMessage.isNullOrBlank()) {
                    val newMessages = currentMessages + Message(replyMessage, "ai")
                    _messages.addAll(newMessages)
                }

                Log.e("API_ERROR", "Mesages list $messages")
            }catch (e: Exception){
                Log.e("API_ERROR", "Error ${e.message}")
                Log.e("API_ERROR", "Mesages list $messages")

                _errorState.value = "${e.message}"
            }
        }
    }

    fun hideErrorState() {
        _errorState.value = null
    }

    fun addUserMessage(content: String) {
        val newMessages = Message(content, "user")
        _messages.add(newMessages)

        generateResponse()
    }

    fun clearMessages(){
        _messages.clear()
    }
}