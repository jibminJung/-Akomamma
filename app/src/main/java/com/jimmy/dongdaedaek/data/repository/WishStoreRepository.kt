package com.jimmy.dongdaedaek.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.jimmy.dongdaedaek.domain.model.Store
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class WishStoreRepository(
    val firebaseFirestore: FirebaseFirestore,
    val dispatcher: CoroutineDispatcher
) {

    suspend fun registerWishStore(email: String, store: Store) = withContext(dispatcher) {
        firebaseFirestore.collection("WishList").document(email)
            .collection("WishStore").document(store.id!!)
            .set(store)


    }

    suspend fun deleteWishStore(email: String, store: Store) = withContext(dispatcher) {

        firebaseFirestore.collection("WishList").document(email)
            .collection("WishStore").document(store.id!!)
            .delete()
            .addOnFailureListener { throw Exception() }
    }

    suspend fun checkUserWishStore(email: String, store: Store): Boolean {
        val registered = firebaseFirestore.collection("WishList").document(email)
            .collection("WishStore").document(store.id!!).get().await()
        return !registered.data.isNullOrEmpty()
    }

    suspend fun loadWishStoreList(email: String):List<Store> =
        firebaseFirestore.collection("WishList").document(email)
            .collection("WishStore").get().await()
            .map { it.toObject<Store>() }

}