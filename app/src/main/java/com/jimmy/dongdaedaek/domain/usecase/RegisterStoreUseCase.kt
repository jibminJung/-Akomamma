package com.jimmy.dongdaedaek.domain.usecase

import com.google.firebase.auth.FirebaseAuth
import com.jimmy.dongdaedaek.NullUserException
import com.jimmy.dongdaedaek.data.repository.StoreRepository
import com.jimmy.dongdaedaek.domain.model.CustomLatLng
import com.jimmy.dongdaedaek.domain.model.Store
import com.naver.maps.geometry.LatLng

class RegisterStoreUseCase(
    val firebaseAuth: FirebaseAuth,
    private val storeRepository: StoreRepository
) {

    suspend operator fun invoke(
        name: String,
        address: String,
        category: List<String>,
        latlng: LatLng
    ) {

        if (firebaseAuth.currentUser == null) {
            throw NullUserException()
        }

        val store = Store(
            title = name,
            category = category,
            address = address,
            latLng = CustomLatLng(latlng.latitude,latlng.longitude),
            numberOfReview = 0,
            rating = "0"
        )
        storeRepository.registerStore(store)


    }


}