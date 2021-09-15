package com.jimmy.dongdaedaek.data.api

import com.jimmy.dongdaedaek.domain.model.Store

interface StoreApi {
    suspend fun getStores():List<Store>
    suspend fun registerStore(store:Store)

}