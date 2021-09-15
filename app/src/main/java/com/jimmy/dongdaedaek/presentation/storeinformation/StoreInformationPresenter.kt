package com.jimmy.dongdaedaek.presentation.storeinformation

import com.jimmy.dongdaedaek.domain.model.Review
import com.jimmy.dongdaedaek.domain.model.Store
import com.jimmy.dongdaedaek.domain.usecase.GetReviewUseCase
import com.jimmy.dongdaedaek.domain.usecase.UploadReviewUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class StoreInformationPresenter(
    override val store: Store,
    val view:StoreInformationContract.View,
    val getReviewUseCase: GetReviewUseCase,
    val uploadReviewUseCase: UploadReviewUseCase
    ):StoreInformationContract.Presenter {

    override val scope: CoroutineScope = MainScope()

    override fun onViewCreated() {
        view.showStoreInfo(store)
        loadReviewData(store)
    }

    override fun onDestroyView() {
    }

    fun loadReviewData(store:Store)=scope.launch {
        try{
            view.showProgressBar()
            view.addReviewData(getReviewUseCase(store.id!!))
        }catch (e:Exception){
            view.showErrorToast()
        }finally {
            view.hideProgressBar()
        }
    }

    fun refreshReviewData(store:Store)=scope.launch {
        try{
            view.showProgressBar()
            view.refreshReviewData(getReviewUseCase(store.id!!))
        }catch (e:Exception){
            view.showErrorToast()
        }finally {
            view.hideProgressBar()
        }
    }

    override fun requestSubmitReview(content: String, rating: Float) {
        scope.launch {
            try {
                view.showProgressBar()
                uploadReviewUseCase(store.id!!,content,rating)
                refreshReviewData(store)
            }catch(e:Exception){
                view.showErrorToast()
            }finally {
                view.hideProgressBar()
            }
        }
    }
}