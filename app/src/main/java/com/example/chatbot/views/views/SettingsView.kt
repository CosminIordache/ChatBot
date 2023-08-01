package com.example.chatbot.views.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chatbot.model.Graph
import com.example.chatbot.utils.AuthManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsView(navController: NavController) {
    val auth = AuthManager()

    Column {
        TopAppBar(
            title = {
                Text(text = "Settings", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigate(Graph.MAIN) }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                actionIconContentColor = MaterialTheme.colorScheme.onSecondary
            )
        )
        Text(text = "Email: ${auth.user()}")

        Button(onClick = {
            auth.signOut(navController = navController)
        }) {
            Text(text = "Sign Out")
        }
    }
}