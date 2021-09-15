package com.jimmy.dongdaedaek.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class CategoryRepository(
    val firestore: FirebaseFirestore,
    val dispatcher: CoroutineDispatcher
) {

    suspend fun loadCategories(): List<Pair<String, String>> = withContext(dispatcher) {
        firestore.collection("category")
            .get()
            .await()
            .map { Pair(it.id, it.get("category_name").toString()) }
    }

}