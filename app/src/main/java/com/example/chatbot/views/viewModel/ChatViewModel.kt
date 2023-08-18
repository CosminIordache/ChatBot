package com.example.chatbot.views.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
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
    private var _messages : MutableLiveData<List<Message>> = MutableLiveData(emptyList())
    var messages: LiveData<List<Message>> = _messages

    private val _errorState = mutableStateOf<String?>(null)
    val errorState = _errorState

    fun generateResponse(){
        val currentMessages = _messages.value.orEmpty()
        val requestBody = OpenAIRequestBody(messages = currentMessages)

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO){
                    api.generateResponse(requestBody)
                }

                val replyMessage = response.choices.firstOrNull()?.message

                if (!replyMessage.isNullOrBlank()) {
                    val newMessages = currentMessages + Message(replyMessage, "ai")
                    _messages.value = newMessages
                }

                Log.e("API_ERROR", "Mesages list ${messages.value}")
            }catch (e: Exception){
                Log.e("API_ERROR", "Error ${e.message}")
                Log.e("API_ERROR", "Mesages list ${messages.value}")

                _errorState.value = "Api error ${e.message}"
            }
        }
    }

    fun hideErrorState() {
        _errorState.value = null
    }

    fun addUserMessage(content: String) {
        val currentMessages = _messages.value.orEmpty()
        val newMessages = currentMessages + Message(content, "user")
        _messages.value = newMessages

        generateResponse()
    }

    fun clearMessages(){
        _messages.value = emptyList()
    }
}