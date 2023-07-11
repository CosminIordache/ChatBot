package com.example.chatbot.views.viewModel

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chatbot.model.Graph
import com.example.chatbot.utils.AuthManager
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainView(
    auth: FirebaseAuth,
    navController: NavController
) {
    Text(text = "Pagina principal ${auth.currentUser?.email}")

    Button(onClick = {
        onLogOutUser(navController = navController)
    }, Modifier.padding(top = 25.dp)) {
        Text(text = "Sign Out")
    }
}

private fun onLogOutUser(navController: NavController) {
    val auth = AuthManager()
    auth.signOut()
    navController.navigate(Graph.SIGNIN)
}

