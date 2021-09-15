package com.jimmy.dongdaedaek.domain.usecase

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetCurrentUserEmail(val firebaseAuth: FirebaseAuth,val dispatcher: CoroutineDispatcher) {
    suspend operator fun invoke():String?= withContext(dispatcher){

        firebaseAuth.currentUser?.email
    }



}