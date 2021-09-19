package com.jimmy.dongdaedaek.domain.usecase

import android.net.Uri
import com.jimmy.dongdaedaek.data.repository.ImageRepository

class UploadPhotosUseCase(private val imageRepository: ImageRepository) {

    suspend operator fun invoke(data:MutableList<Uri>):List<String>{

        val newList = mutableListOf<String>()
        imageRepository.uploadPhotos(data).forEach {
            if(!it.contentEquals("Error")){
                newList.add(it)
            }
        }
       return newList.toList()
    }
}