package com.jimmy.dongdaedaek.data.repository

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class CategoryRepository(
    private val firestore: FirebaseFirestore,
    val dispatcher: CoroutineDispatcher
) {

    suspend fun loadCategories(): List<Pair<String, String>> = withContext(dispatcher) {
        firestore.collection("category")
            .get()
            .await()
            .map { Pair(it.id, it.get("category_name").toString()) }
    }
    suspend fun registerCategories(newCategoryName:List<String>) = withContext(dispatcher){

        firestore.runBatch { batch ->
            newCategoryName.forEach {
                val newRf = firestore.collection("category").document()
                batch.set(newRf, hashMapOf("category_name" to it))
            }
        }

    }

}