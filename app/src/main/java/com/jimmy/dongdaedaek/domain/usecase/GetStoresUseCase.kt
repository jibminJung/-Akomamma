package com.jimmy.dongdaedaek.domain.usecase

import com.jimmy.dongdaedaek.data.repository.StoreRepository
import com.jimmy.dongdaedaek.domain.model.Store

class GetStoresUseCase(
    private val storeRepository: StoreRepository) {

    suspend operator fun invoke():List<Store> =storeRepository.getStores()

}