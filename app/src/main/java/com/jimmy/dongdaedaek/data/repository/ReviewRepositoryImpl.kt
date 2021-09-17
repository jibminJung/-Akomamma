package com.jimmy.dongdaedaek.data.repository

import com.jimmy.dongdaedaek.data.api.ReviewApi
import com.jimmy.dongdaedaek.domain.model.Review
import com.jimmy.dongdaedaek.domain.model.Store
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ReviewRepositoryImpl(
    val reviewApi:ReviewApi,
    val dispatcher: CoroutineDispatcher,
):ReviewRepository {
    override suspend fun getStoreReview(storeId: String): List<Review> = withContext(dispatcher){

        reviewApi.getStoreReview(storeId)
    }

    override suspend fun uploadReview(review: Review): Review = withContext(dispatcher){
        reviewApi.uploadReview(review)
    }

}