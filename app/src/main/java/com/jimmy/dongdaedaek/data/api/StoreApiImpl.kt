package com.jimmy.dongdaedaek.data.api

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.jimmy.dongdaedaek.domain.model.Store
import kotlinx.coroutines.tasks.await

class StoreApiImpl(
    private val firebaseFirestore: FirebaseFirestore
):StoreApi {
    override suspend fun getStores(): List<Store> =
        firebaseFirestore.collection("stores")
            .get()
            .await()
            .map { it.toObject<Store>() }

    override suspend fun registerStore(store: Store) :Store{
        val newStoreRef = firebaseFirestore.collection("stores").document()
        firebaseFirestore.runTransaction {transaction->
            transaction.set(newStoreRef,store)
        }.await()

        return newStoreRef.get().await().toObject<Store>()!!

    }

    override suspend fun getStoreById(storeId: String): Store =
        firebaseFirestore.collection("stores")
            .document(storeId)
            .get()
            .await()
            .toObject<Store>()!!

    override suspend fun getFilteredStore(checkedCategory: MutableList<String>): List<Store> =
        firebaseFirestore.collection("stores")
            .whereArrayContainsAny("category", checkedCategory)
            .get()
            .await()
            .map{it.toObject<Store>()}
}