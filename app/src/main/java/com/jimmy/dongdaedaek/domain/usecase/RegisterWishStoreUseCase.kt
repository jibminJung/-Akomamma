package com.jimmy.dongdaedaek.domain.usecase

import com.google.firebase.auth.FirebaseAuth
import com.jimmy.dongdaedaek.NullUserException
import com.jimmy.dongdaedaek.data.repository.WishStoreRepository
import com.jimmy.dongdaedaek.domain.model.Store

class RegisterWishStoreUseCase(val firebaseAuth: FirebaseAuth, private val wishStoreRepository: WishStoreRepository) {
    suspend operator fun invoke(store : Store){
        if(firebaseAuth.currentUser != null){
            val email = firebaseAuth.currentUser!!.uid!!
            wishStoreRepository.registerWishStore(email, store)
        }else{
            throw NullUserException()
        }
    }
}