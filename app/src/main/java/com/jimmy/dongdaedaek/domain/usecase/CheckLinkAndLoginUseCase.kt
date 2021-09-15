package com.jimmy.dongdaedaek.domain.usecase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.jimmy.dongdaedaek.data.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CheckLinkAndLoginUseCase(
    val firebaseAuth: FirebaseAuth,
    val userRepository: UserRepository,
    val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(emailLink:String)=
        withContext(dispatcher){
            if (firebaseAuth.isSignInWithEmailLink(emailLink)) {
                // Retrieve this from wherever you stored it
                val email = userRepository.getUser()?.id?:""

                // The client SDK will parse the code from the link for you.
                firebaseAuth.signInWithEmailLink(email, emailLink)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("logging in...", "Successfully signed in with email link!")
                            val result = task.result
                            // You can access the new user via result.getUser()
                            // Additional user info profile *not* available via:
                            // result.getAdditionalUserInfo().getProfile() == null
                            // You can check if the user is new or existing:
                            // result.getAdditionalUserInfo().isNewUser()
                        } else {
                            Log.d("logging in...", "Error signing in with email link", task.exception)
                        }
                    }
            }

        }


}