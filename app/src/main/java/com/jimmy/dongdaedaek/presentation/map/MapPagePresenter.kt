package com.jimmy.dongdaedaek.presentation.map

import android.util.Log
import com.jimmy.dongdaedaek.data.repository.CategoryRepository
import com.jimmy.dongdaedaek.domain.usecase.GetFilteredStoreUseCase
import com.jimmy.dongdaedaek.domain.usecase.GetStoresUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MapPagePresenter(
    val view: MapPageContract.View,
    val getStoresUseCase: GetStoresUseCase,
    val getFilteredStoreUseCase: GetFilteredStoreUseCase,
    val getCategoryRepository: CategoryRepository
) : MapPageContract.Presenter {
    override val scope: CoroutineScope = MainScope()

    override fun onViewCreated() {
        loadCategories()
        fetchFilteredStore(mutableListOf())
    }

    override fun onDestroyView() {

    }
    override fun loadCategories() {
        scope.launch {
            try{
                view.showProgressBar()
                view.initCategory(getCategoryRepository.loadCategories())
            }catch (e:Exception){
                view.showToastMsg("카테고리 로드 중 에러가 발생했습니다.")
            }finally {
                view.hideProgressBar()
            }

        }
    }

    override fun fetchFilteredStore(checkedCategory: MutableList<String>) {
        scope.launch {
            try {
                view.showProgressBar()
                if (checkedCategory.isEmpty()) {
                    val stores = getStoresUseCase()
                    view.showStoresMark(stores)
                } else {
                    val stores = getFilteredStoreUseCase(checkedCategory)
                    view.showStoresMark(stores)
                }
            } catch (e: Exception) {
                view.showErrorToast()
            } finally {
                view.hideProgressBar()
            }
        }
    }

}