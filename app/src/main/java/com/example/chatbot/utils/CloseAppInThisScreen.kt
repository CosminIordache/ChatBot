package com.example.chatbot.utils

import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun CloseAppInThisScreen(){
    val activity = LocalContext.current as ComponentActivity
    val onBackPressedCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity.apply {
                    finish()
                    moveTaskToBack(true)
                }
            }
        }
    }
    DisposableEffect(Unit) {
        activity.onBackPressedDispatcher.addCallback(onBackPressedCallback)
        onDispose {
            onBackPressedCallback.remove()
        }
    }
}