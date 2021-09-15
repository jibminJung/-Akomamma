package com.jimmy.dongdaedaek.data.repository

import com.jimmy.dongdaedaek.domain.model.Review

interface ReviewRepository {
    suspend fun getStoreReview(storeId:String):List<Review>
    suspend fun uploadReview(review:Review):Review
}