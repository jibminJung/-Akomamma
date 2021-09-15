package com.jimmy.dongdaedaek.domain.usecase

import com.jimmy.dongdaedaek.data.repository.ReviewRepository
import com.jimmy.dongdaedaek.domain.model.Review

class UploadReviewUseCase(private val reviewRepository: ReviewRepository) {
    suspend operator fun invoke(storeId:String,content:String,rating:Float):Review {

        val review = Review(
            storeId=storeId,
            reviewText = content,
            rating = rating,
            userId = "temp")

       return reviewRepository.uploadReview(review)
    }
}