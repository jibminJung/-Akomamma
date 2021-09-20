package com.jimmy.dongdaedaek.presentation.wishstorelist

import com.jimmy.dongdaedaek.NullUserException
import com.jimmy.dongdaedaek.domain.usecase.GetStoreByIdUseCase
import com.jimmy.dongdaedaek.domain.usecase.LoadWishStoreListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class WishStoreListPresenter(
    val view: WishStoreListContract.View,
    val loadWishStoreList: LoadWishStoreListUseCase,
    val getStoreById: GetStoreByIdUseCase
) : WishStoreListContract.Presenter {
    override val scope: CoroutineScope = MainScope()

    override fun onViewCreated() {

        fetchWishStoreList()

    }

    override fun fetchWishStoreList() {
        scope.launch {
            try {
                val data = loadWishStoreList()
                view.addDataToRecycler(data)
            } catch (e: NullUserException) {
                view.showToastMsg("로그인 후 이용 가능합니다.")
            } catch (e: Exception) {
                view.showToastMsg("예기치 못한 에러가 발생했습니다.")
            }
        }
    }

    override fun getStore(id: String) {
        scope.launch {
            try {
                val store = getStoreById(id)
                view.goToStoreInformation(store)
            } catch (e: Exception) {
                view.showToastMsg("예기치 못한 에러가 발생했습니다.")
            }
        }
    }

    override fun onDestroyView() {

    }
}