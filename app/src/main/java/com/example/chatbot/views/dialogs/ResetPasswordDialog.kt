package com.example.chatbot.views.dialogs

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.chatbot.model.Util
import com.example.chatbot.utils.AuthManager


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordDialog(
    navController: NavController,
    title: String,
    activity: Activity
){
    val authManager = AuthManager()
    var resetEmail by rememberSaveable { mutableStateOf("") }

    Dialog(onDismissRequest = { navController.navigate(Util.SIGNIN) }) {
        Surface(
            shadowElevation = 8.dp,
            shape = RoundedCornerShape(16.dp), tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    IconButton(onClick = { navController.navigate(Util.SIGNIN) }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.padding(start = 40.dp))

                    Text(text = title, fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(top = 14.dp))
                }

                OutlinedTextField(
                    value = resetEmail,
                    onValueChange = { resetEmail = it },
                    label = { Text("Email") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Email, contentDescription = "Email")
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(top = 10.dp, start = 16.dp, end = 16.dp)
                )

                Button(
                    onClick = { authManager.resetPassword(email = resetEmail, activity = activity) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "Reset password", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
