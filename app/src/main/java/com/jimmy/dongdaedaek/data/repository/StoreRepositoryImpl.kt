package com.jimmy.dongdaedaek.data.repository

import com.jimmy.dongdaedaek.data.api.StoreApi
import com.jimmy.dongdaedaek.domain.model.Store
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class StoreRepositoryImpl(
    private val storeApi:StoreApi,
    val dispatcher: CoroutineDispatcher
):StoreRepository {
    override suspend fun getStores(): List<Store> = withContext(dispatcher){
        storeApi.getStores()
    }

    override suspend fun registerStore(store: Store) = withContext(dispatcher){
        storeApi.registerStore(store)
    }

    override suspend fun getStoreById(storeId: String): Store {
        return storeApi.getStoreById(storeId)
    }

    override suspend fun getFilteredStore(checkedCategory: MutableList<String>): List<Store> = withContext(dispatcher){
        storeApi.getFilteredStore(checkedCategory)
    }


}