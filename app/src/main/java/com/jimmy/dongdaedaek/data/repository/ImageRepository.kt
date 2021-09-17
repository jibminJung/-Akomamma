package com.jimmy.dongdaedaek.data.repository

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ImageRepository(
    val firebaseStorage: FirebaseStorage,
    val dispatcher: CoroutineDispatcher
) {

    suspend fun uploadPhotos(photos:MutableList<Uri>) = withContext(dispatcher){
        val uploaded:List<String> = photos.mapIndexed { index, uri ->
            try{
                val fileName = (uri.toString() + "${System.currentTimeMillis()}").hashCode().toString()
                firebaseStorage.reference.child("photos/").child(fileName)
                    .putFile(uri)
                    .await()
                    .storage
                    .downloadUrl
                    .await()
                    .toString()
            }catch (e:Exception){
                "Error"
            }
        }
        return@withContext uploaded
    }
}