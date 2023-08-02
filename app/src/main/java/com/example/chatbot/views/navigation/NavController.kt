package com.example.chatbot.views.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatbot.R
import com.example.chatbot.views.views.authentification.SignInScreen
import com.example.chatbot.views.views.authentification.SignUpScreen
import com.example.chatbot.model.Util
import com.example.chatbot.utils.AuthManager
import com.example.chatbot.views.dialogs.EmailVerificationDialog
import com.example.chatbot.views.dialogs.ResetPasswordDialog
import com.example.chatbot.views.viewModel.ChatViewModel
import com.example.chatbot.views.views.Chatscreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun NavController(activity: Activity) {
    val navController = rememberNavController()

    //auth
    val auth = Firebase.auth
    val authManager = AuthManager()
    val chatViewModel = viewModel<ChatViewModel>()

    NavHost(
        navController,
        startDestination = if (authManager.isUserLoggedIn()) Util.MAIN else Util.SIGNIN
    ) {
        composable(Util.MAIN) {
            Chatscreen(
                navController = navController,
                viewModel = chatViewModel
            )
        }
        composable(Util.SIGNIN) {
            SignInScreen(
                auth = auth,
                navController = navController,
                activity = activity
            )
        }
        composable(Util.SIGNUP) {
            SignUpScreen(
                navController = navController,
                auth = auth,
                activity = activity
            )
        }

        composable(Util.DIALOGEMAILCONFIRMATION) {
            EmailVerificationDialog(
                auth = auth,
                navController = navController,
                activity = activity,
                title = activity.getString(R.string.titleConfirmEmail),
                message = activity.getString(R.string.messageConfirmEmail)
            )
        }

        composable(Util.DIALOGRESETPASSWORD) {
            ResetPasswordDialog(
                navController = navController,
                title = activity.getString(R.string.titleResetPassword),
                activity = activity
            )
        }
    }
}