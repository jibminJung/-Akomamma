package com.jimmy.dongdaedaek.data.repository

import com.jimmy.dongdaedaek.data.api.ReviewApi
import com.jimmy.dongdaedaek.domain.model.Review
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ReviewRepositoryImpl(
    private val reviewApi:ReviewApi,
    val dispatcher: CoroutineDispatcher,
):ReviewRepository {
    override suspend fun getStoreReview(storeId: String): List<Review> = withContext(dispatcher){

        reviewApi.getStoreReview(storeId)
    }

    override suspend fun uploadReview(review: Review): Review = withContext(dispatcher){
        reviewApi.uploadReview(review)
    }

    override suspend fun getRecentReview(): List<Review> = withContext(dispatcher){
        reviewApi.getRecentReviews()
    }
}