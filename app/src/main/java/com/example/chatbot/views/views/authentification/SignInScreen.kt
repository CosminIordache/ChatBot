@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.chatbot.views.views.authentification

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chatbot.R
import com.example.chatbot.model.Graph
import com.example.chatbot.utils.AuthManager
import com.example.chatbot.utils.CloseAppInThisScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SignInScreen(auth: FirebaseAuth, navController: NavController, activity: Activity) {
    val context = LocalContext.current
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var passwordVisibility by remember { mutableStateOf(false) }

    val icon = if (passwordVisibility){
        painterResource(id = R.drawable.baseline_visibility_24)
    }else{
        painterResource(id = R.drawable.baseline_visibility_off_24)
    }

    CloseAppInThisScreen()

    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
            .clip(RoundedCornerShape(16.dp)),
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.chatbotlogo),
                contentDescription = "Logo"
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Sign In",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Column() {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Email, contentDescription = "Email" )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(top = 10.dp, start = 25.dp, end = 25.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Lock, contentDescription = "Email" )
                },
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        Icon(
                            painter = icon,
                            contentDescription = "Visibility"
                        )
                    }
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(top = 5.dp, start = 25.dp, end = 25.dp, bottom = 20.dp)
            )

            Button(
                onClick = {
                    signInUser(
                        email = email,
                        password = password,
                        auth = auth,
                        context = context,
                        navController = navController,
                        activity = activity
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp, end = 25.dp)
                    .height(50.dp)
            ) {
                Text(text = "Sing In", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Don't have account ?")
                TextButton(onClick = { navController.navigate(Graph.SIGNUP)}) {
                    Text(text = "Sing Up")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { navController.navigate(Graph.DIALOGRESETPASSWORD)}) {
                    Text(text = "Don't remember your password ?")
                }
            }

//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                IconButton(onClick = { }) {
//                    Image(
//                        painter = painterResource(id = R.drawable.google),
//                        contentDescription = "Google logo"
//                    )
//                }
//                IconButton(onClick = { }) {
//                    Image(
//                        painter = painterResource(id = R.drawable.facebook),
//                        contentDescription = "facebook logo"
//                    )
//                }
//            }

        }
    }
}

private fun signInUser(
    email: String,
    password: String,
    auth: FirebaseAuth,
    context: Context,
    navController: NavController,
    activity: Activity
) {
    val authManager = AuthManager()

    if (email.isNotEmpty() && password.isNotEmpty()) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                auth.currentUser?.reload()?.addOnCompleteListener { reloadTask ->
                    if (reloadTask.isSuccessful) {
                        authManager.checkUser(auth, navController, activity)
                    } else {
                        Toast.makeText(
                            context,
                            "Error al actualizar el usuario",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                task.exception?.let {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}