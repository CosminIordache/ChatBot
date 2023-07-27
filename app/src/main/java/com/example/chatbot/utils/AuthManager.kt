package com.example.chatbot.utils

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import com.example.chatbot.R
import com.example.chatbot.model.Graph
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest

class AuthManager {
    private val auth = FirebaseAuth.getInstance()

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun signOut(navController: NavController) {
        auth.signOut()
        navController.navigate(Graph.SIGNIN)
    }

    fun user() = auth.currentUser?.email.toString()


    fun checkUser(auth: FirebaseAuth, navController: NavController, activity: Activity) {
        val currentuser = auth.currentUser
        val admin = currentuser?.email.equals("admin@admin.com")
        if (currentuser != null && currentuser.isEmailVerified || admin) {
            navController.navigate(Graph.MAIN){
                popUpTo(Graph.SIGNIN) { inclusive = true}
                popUpTo(Graph.SIGNUP) { inclusive = true}
            }
        } else {
            Toast.makeText(activity, "Email not verified", Toast.LENGTH_SHORT).show()
        }
    }

    fun sendEmailVerification(auth: FirebaseAuth, activity: Activity) {
        val user = auth.currentUser
        user?.sendEmailVerification()?.addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    activity,
                    "Check your email and verify your identity",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun onConfirmClick(auth: FirebaseAuth, navController: NavController, activity: Activity) {
        val profileUpdate = userProfileChangeRequest { }
        val authManager = AuthManager()

        auth.currentUser!!.updateProfile(profileUpdate).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (auth.currentUser!!.isEmailVerified) {
                    authManager.checkUser(auth, navController, activity)
                } else {
                    Toast.makeText(activity, "Please verify your account", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

}