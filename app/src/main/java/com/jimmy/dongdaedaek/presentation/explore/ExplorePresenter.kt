package com.jimmy.dongdaedaek.presentation.explore

import android.net.Uri
import android.util.Log
import com.jimmy.dongdaedaek.domain.usecase.CheckLinkAndLoginUseCase
import com.jimmy.dongdaedaek.domain.usecase.GetStoresUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ExplorePresenter(private val view: ExploreContract.View,
val getStoresUseCase: GetStoresUseCase,
val checkLinkAndLogin:CheckLinkAndLoginUseCase):ExploreContract.Presenter {
    override val scope: CoroutineScope = MainScope()

    override fun onViewCreated() {
        fetchStores()
    }

    override fun onDestroyView() {

    }
    private fun fetchStores() = scope.launch {
        try{
            view.showProgressBar()
            val stores = getStoresUseCase()
            view.showStores(stores)
        }catch (e:Exception){

        }finally {
            view.hideProgressBar()
        }
    }

    override fun checkLogin(uri: String) {
        scope.launch {
            try {
                Log.d("logging in..","start checkLinkAndLogin from presenter")
                view.showProgressBar()
                checkLinkAndLogin(uri)
                view.showLoginSuccessToast()
            } catch (e: Exception) {
                view.showErrorToast()
            } finally {
                view.hideProgressBar()
            }
        }
    }
}