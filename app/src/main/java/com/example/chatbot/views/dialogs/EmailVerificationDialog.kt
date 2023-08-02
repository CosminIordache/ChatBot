package com.example.chatbot.views.dialogs

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.chatbot.model.Util
import com.example.chatbot.utils.AuthManager
import com.google.firebase.auth.FirebaseAuth


@Composable
fun EmailVerificationDialog(
    auth: FirebaseAuth,
    navController: NavController,
    activity: Activity,
    title: String,
    message: String
) {
    val authManager = AuthManager()

    Dialog(onDismissRequest = { navController.navigate(Util.SIGNUP) }) {
        Surface(
            shadowElevation = 8.dp,
            shape = RoundedCornerShape(16.dp), tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = { navController.navigate(Util.SIGNUP) }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        modifier = Modifier.align(Alignment.End)
                    )
                }

                Spacer(modifier = Modifier.padding(top = 5.dp))

                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 25.sp)

                Spacer(modifier = Modifier.padding(top = 8.dp))

                Text(text = message)

                Spacer(modifier = Modifier.padding(top = 12.dp))

                Button(onClick = {
                    authManager.onConfirmClick(
                        auth,
                        navController,
                        activity
                    )
                }) {
                    Text(text = "Confirm")
                }

            }
        }
    }
}

