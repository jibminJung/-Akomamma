package com.jimmy.dongdaedaek.domain.usecase

import com.jimmy.dongdaedaek.data.repository.StoreRepository
import com.jimmy.dongdaedaek.domain.model.Store

class GetStoreByIdUseCase (private val storeRepository: StoreRepository){

    suspend operator fun invoke(storeId:String) : Store =
        storeRepository.getStoreById(storeId)
}
