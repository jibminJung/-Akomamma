package com.jimmy.dongdaedaek.domain.usecase

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class LogoutUseCase(
    val firebaseAuth: FirebaseAuth,
    val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke() = withContext(dispatcher){
        firebaseAuth.signOut()
    }
}