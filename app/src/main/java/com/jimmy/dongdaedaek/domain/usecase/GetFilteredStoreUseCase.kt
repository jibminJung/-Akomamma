package com.jimmy.dongdaedaek.domain.usecase

import com.jimmy.dongdaedaek.data.repository.StoreRepository
import com.jimmy.dongdaedaek.domain.model.Store

class GetFilteredStoreUseCase(val storeRepository: StoreRepository) {
    suspend operator fun invoke(checkedCategoryIds:MutableList<String>):List<Store> =
        storeRepository.getFilteredStore(checkedCategoryIds)


}