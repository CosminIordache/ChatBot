@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.chatbot.views.views

import android.annotation.SuppressLint
import android.os.Message
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chatbot.R
import com.example.chatbot.utils.AuthManager
import com.example.chatbot.utils.CloseAppInThisScreen
import com.example.chatbot.views.viewModel.ChatViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chatscreen(
    navController: NavController,
    viewModel: ChatViewModel
) {

    CloseAppInThisScreen()

    val authManager = AuthManager()
    val errorState = viewModel.errorState.value

    Scaffold(
        topBar = { TopBar(authManager = authManager, navController = navController) }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            if (errorState != null){
                AlertDialog(
                    onDismissRequest = { viewModel.clearErrorState() },
                    title = { Text(text = "API ERROR", fontWeight = FontWeight.Bold) },
                    text = { Text(text = errorState) },
                    confirmButton = {
                        Button(onClick = { viewModel.clearErrorState() }) {
                            Text(text = "Aceptar")
                        }
                    },
                    shape = RoundedCornerShape(16.dp)
                )
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                reverseLayout = true
            ) {
                items(viewModel.messages.value!!.reversed()) { message ->
                    if (message.role == "user") {
                        MessageCard(message.content, Alignment.Start)
                    } else {
                        MessageCard(message.content, Alignment.End)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var userMessage by rememberSaveable { mutableStateOf("") }

                IconButton(
                    onClick = {
                        viewModel.clearMessages()
                    },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(top = 10.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .background(Color.Transparent),
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Clear Conversation")
                }

                OutlinedTextField(
                    value = userMessage,
                    onValueChange = { userMessage = it },
                    label = { Text("Write a message") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(onSend = {
                        viewModel.addUserMessage(userMessage)
                        userMessage = ""
                    }),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = {
                        if (userMessage.isNotEmpty()) {
                            viewModel.addUserMessage(userMessage)
                            userMessage = ""
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(top = 10.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .background(Color.Transparent),
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Send message")
                }
            }
        }

    }
}


@Composable
fun TopBar(authManager: AuthManager, navController: NavController) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                text = "Hi ${authManager.user()}",
                style = TextStyle(
                    fontSize = 18.sp
                )
            )
        },
        actions = {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
            }
            DropdownMenu(expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(onClick = { authManager.signOut(navController) })
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onSecondary
        )
    )
}

@Composable
fun DropdownMenuItem(onClick: () -> Unit) {
    Row(Modifier.padding(horizontal = 15.dp, vertical = 5.dp)) {
        Text(
            text = "Sign Out",
            style = TextStyle(
                fontSize = 16.sp
            ),
            modifier = Modifier.clickable(onClick = onClick)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Icon(
            painter = painterResource(id = R.drawable.baseline_person_off_24),
            contentDescription = "Out"
        )
    }
}

@Composable
fun MessageCard(text: String, alignment: Alignment.Horizontal) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 4.dp,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .wrapContentWidth(alignment)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(16.dp),
            fontSize = 16.sp,
            textAlign = when (alignment) {
                Alignment.Start -> TextAlign.Start
                Alignment.End -> TextAlign.End
                else -> TextAlign.Start
            }
        )
    }
}


