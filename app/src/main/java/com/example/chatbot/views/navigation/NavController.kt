package com.example.chatbot.views.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatbot.R
import com.example.chatbot.views.viewModel.authentification.SignInScreen
import com.example.chatbot.views.viewModel.authentification.SignUpScreen
import com.example.chatbot.model.Graph
import com.example.chatbot.utils.AuthManager
import com.example.chatbot.views.dialogs.EmailVerificationDialog
import com.example.chatbot.views.viewModel.MainView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun NavController(activity: Activity) {
    val navController = rememberNavController()

    //auth
    val auth = Firebase.auth
    val authManager = AuthManager()

    NavHost(
        navController,
        startDestination = if (authManager.isUserLoggedIn()) Graph.MAIN else Graph.SIGNIN
    ) {
        composable(Graph.MAIN) {
            MainView(
                auth = auth,
                navController = navController
            )
        }
        composable(Graph.SIGNIN) {
            SignInScreen(
                auth = auth,
                navController = navController,
                activity = activity
            )
        }
        composable(Graph.SIGNUP) {
            SignUpScreen(
                navController = navController,
                auth = auth,
                activity = activity
            )
        }
        composable(Graph.DIALOGEMAILCONFIRMATION) {
            EmailVerificationDialog(
                auth = auth,
                navController = navController,
                activity = activity,
                title = activity.getString(R.string.titleConfirmEmail),
                message = activity.getString(R.string.messageConfirmEmail)
            )
        }
    }
}