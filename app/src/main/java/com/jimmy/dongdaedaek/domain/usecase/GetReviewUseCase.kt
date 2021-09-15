package com.jimmy.dongdaedaek.domain.usecase

import com.jimmy.dongdaedaek.data.repository.ReviewRepository
import com.jimmy.dongdaedaek.domain.model.Review

class GetReviewUseCase(val reviewRepository: ReviewRepository) {
    suspend operator fun invoke(storeId:String):List<Review> =
        reviewRepository.getStoreReview(storeId)

}