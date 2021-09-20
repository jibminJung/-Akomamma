package com.jimmy.dongdaedaek.domain.usecase

import com.google.firebase.auth.FirebaseAuth
import com.jimmy.dongdaedaek.NullUserException
import com.jimmy.dongdaedaek.data.repository.ReviewRepository
import com.jimmy.dongdaedaek.domain.model.Review

class UploadReviewUseCase(private val reviewRepository: ReviewRepository,
private val firebaseAuth: FirebaseAuth) {
    suspend operator fun invoke(storeId:String,content:String,rating:Float,downloadUrls:List<String>?):Review {
        val username = firebaseAuth.currentUser?.uid?:throw NullUserException()
        val review = Review(
            storeId=storeId,
            reviewText = content,
            rating = rating,
            userId = username,
        photos = downloadUrls)

       return reviewRepository.uploadReview(review)
    }
}