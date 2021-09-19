package com.jimmy.dongdaedaek.domain.usecase

import com.jimmy.dongdaedaek.data.repository.ReviewRepository

class GetRecentReviewUseCase(private val reviewRepository: ReviewRepository) {
    suspend operator fun invoke()=
        reviewRepository.getRecentReview()

}
