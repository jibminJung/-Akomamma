package com.jimmy.dongdaedaek.domain.usecase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.actionCodeSettings
import com.jimmy.dongdaedaek.data.repository.UserRepository
import com.jimmy.dongdaedaek.domain.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RequestLoginUseCase(
    val firebaseAuth: FirebaseAuth,
    private val userRepository: UserRepository,
    val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(email: String)=
        withContext(dispatcher) {
            Log.d("logging in..","email function called...")
            userRepository.saveUser(User(email))
            val actionCodeSettings = actionCodeSettings {
                // URL you want to redirect back to. The domain (www.example.com) for this
                // URL must be whitelisted in the Firebase Console.
                url = "https://dongdaedaek.page.link/tVfas877SHYG"
                // This must be true
                handleCodeInApp = true
                setAndroidPackageName(
                    "com.jimmy.dongdaedaek",
                    false, /* installIfNotAvailable */
                    "30" /* minimumVersion */
                )
            }
            firebaseAuth.sendSignInLinkToEmail(email, actionCodeSettings).addOnCompleteListener {
                if(it.isSuccessful){
                    Log.d("logging in..","email sent successfully...")
                }else{

                    Log.d("logging in..","email sent failed...")
                }
            }

        }


}

