package com.jimmy.dongdaedaek.domain.usecase

import com.google.firebase.auth.FirebaseAuth
import com.jimmy.dongdaedaek.NullUserException
import com.jimmy.dongdaedaek.data.repository.WishStoreRepository
import com.jimmy.dongdaedaek.domain.model.Store

class LoadWishStoreListUseCase(val firebaseAuth: FirebaseAuth, val wishStoreRepository: WishStoreRepository) {
    suspend operator fun invoke():List<Store>{
        if (firebaseAuth.currentUser != null){
            val email = firebaseAuth.currentUser!!.email!!
            return wishStoreRepository.loadWishStoreList(email)
        }else{
            throw NullUserException()
        }
    }
}