package com.jimmy.dongdaedaek.data.repository

import android.content.Context
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.jimmy.dongdaedaek.extension.ImageUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File

class ImageRepository(
    private val firebaseStorage: FirebaseStorage,
    val dispatcher: CoroutineDispatcher,
    val context: Context
) {

    suspend fun uploadPhotos(photos:MutableList<Uri>) = withContext(dispatcher){
        val uploaded:List<String> = photos.mapIndexed { _, uri ->
            try{
                val fileName = (uri.toString() + "${System.currentTimeMillis()}").hashCode().toString()
                val reduced = ImageUtil.resizeAndCompressImageBeforeSend(context,uri,fileName)
                firebaseStorage.reference.child("photos/").child(fileName)
                    .putFile(Uri.fromFile(File(reduced)))
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