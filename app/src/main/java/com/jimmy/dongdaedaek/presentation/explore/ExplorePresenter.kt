package com.jimmy.dongdaedaek.presentation.explore

import android.net.Uri
import android.util.Log
import com.jimmy.dongdaedaek.data.repository.CategoryRepository
import com.jimmy.dongdaedaek.domain.model.Store
import com.jimmy.dongdaedaek.domain.usecase.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ExplorePresenter(
    private val view: ExploreContract.View,
    val categoryRepository: CategoryRepository,
    val getStoresUseCase: GetStoresUseCase,
    val checkLinkAndLogin: CheckLinkAndLoginUseCase,
    val getRecentReviews: GetRecentReviewUseCase,
    val getStoreByIdUseCase: GetStoreByIdUseCase,
    val getFilteredStoreUseCase: GetFilteredStoreUseCase
) : ExploreContract.Presenter {
    override val scope: CoroutineScope = MainScope()

    override fun onViewCreated() {
        fetchCategories()
        fetchStores()
        fetchRecentReview()
    }

    override fun getStoreById(storeId: String) {
        scope.launch {
            try {
                val store = getStoreByIdUseCase(storeId)
                view.goToStore(store)
            } catch (e: Exception) {
                view.showErrorToast()

            }

        }

    }

    override fun onDestroyView() {

    }

    private fun fetchStores() = scope.launch {
        try {
            view.showProgressBar()
            val stores = getStoresUseCase()
            view.showStores(stores)
        } catch (e: Exception) {
            Log.e("fetch store exception", e.message ?: "")
            view.showErrorToast()
        } finally {
            view.hideProgressBar()
        }
    }

    override fun fetchFilteredStore(checkedCategory: MutableList<String>) {
        scope.launch {
            try {
                view.showProgressBar()
                if(checkedCategory.isEmpty()){
                    val stores = getStoresUseCase()
                    view.showStores(stores)
                }else{
                    val stores = getFilteredStoreUseCase(checkedCategory)
                    view.showStores(stores)
                }
            } catch (e: Exception) {
                view.showErrorToast()
            } finally {
                view.hideProgressBar()
            }
        }
    }

    override fun fetchRecentReview() {
        scope.launch {
            try {
                view.showProgressBar()
                val recentReviews = getRecentReviews()
                view.initViewPager(recentReviews)
            } catch (e: Exception) {
                view.showErrorToast()
            } finally {
                view.hideProgressBar()
            }
        }
    }

    fun fetchCategories() {
        scope.launch {
            try {
                view.showProgressBar()
                val cats = categoryRepository.loadCategories()
                view.initCategory(cats)
            } catch (e: Exception) {
                view.showErrorToast()
            } finally {
                view.hideProgressBar()
            }
        }
    }


    override fun checkLogin(uri: String) {
        scope.launch {
            try {
                Log.d("logging in..", "start checkLinkAndLogin from presenter")
                view.showProgressBar()
                checkLinkAndLogin(uri)
                view.showLoginSuccessToast()
            } catch (e: Exception) {
                Log.e("login check exception", e.message ?: "")
                view.showErrorToast()
            } finally {
                view.hideProgressBar()
            }
        }
    }
}