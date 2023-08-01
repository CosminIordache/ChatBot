package com.example.chatbot.views.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chatbot.R
import com.example.chatbot.model.Graph
import com.example.chatbot.utils.CloseAppInThisScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatView(
    navController: NavController
) {

    CloseAppInThisScreen()

    Column {
        TopAppBar(
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.bot),
                        contentDescription = "logo",
                        modifier = Modifier.size(35.dp)
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    navController.navigate(Graph.SETTINGS)
                }) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
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
}

