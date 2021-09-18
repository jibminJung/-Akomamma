package com.jimmy.dongdaedaek.data.api

import com.jimmy.dongdaedaek.domain.model.Review

interface ReviewApi {
    suspend fun getStoreReview(storeId:String):List<Review>
    suspend fun uploadReview(review: Review):Review
    suspend fun getRecentReviews():List<Review>
}