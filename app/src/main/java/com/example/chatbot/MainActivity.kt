package com.example.chatbot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatbot.views.viewModel.authentification.SignInScreen
import com.example.chatbot.views.viewModel.authentification.SignUpScreen
import com.example.chatbot.model.Graph
import com.example.chatbot.ui.theme.ChatBotTheme
import com.example.chatbot.utils.AuthManager
import com.example.chatbot.views.navigation.NavController
import com.example.chatbot.views.viewModel.MainView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatBotTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavController(this)
                }
            }
        }
    }
}


