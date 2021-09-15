package com.jimmy.dongdaedaek.data.repository

import com.jimmy.dongdaedaek.domain.model.Store

interface StoreRepository {
    suspend fun getStores():List<Store>
    suspend fun registerStore(store:Store)
}