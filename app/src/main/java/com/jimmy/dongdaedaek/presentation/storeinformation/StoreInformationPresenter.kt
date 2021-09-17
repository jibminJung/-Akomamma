package com.jimmy.dongdaedaek.presentation.storeinformation

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.jimmy.dongdaedaek.NullUserException
import com.jimmy.dongdaedaek.domain.model.Store
import com.jimmy.dongdaedaek.domain.usecase.GetReviewUseCase
import com.jimmy.dongdaedaek.domain.usecase.UploadPhotosUseCase
import com.jimmy.dongdaedaek.domain.usecase.UploadReviewUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class StoreInformationPresenter(
    override val store: Store,
    val view:StoreInformationContract.View,
    val firebaseAuth: FirebaseAuth,
    val getReviewUseCase: GetReviewUseCase,
    val uploadReviewUseCase: UploadReviewUseCase,
    val uploadPhotosUseCase: UploadPhotosUseCase
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

    override fun requestSubmitReview(content: String, rating: Float, data: MutableList<Uri>?) {
        scope.launch {
            try {
                view.showProgressBar()
                val downloadUrls= data?.let{
                    view.showToastMsg("업로드 중..")
                    uploadPhotosUseCase(data)
                }
                uploadReviewUseCase(store.id!!, content,rating,downloadUrls)
                view.clearImageInput()
                refreshReviewData(store)
                view.showToastMsg("등록이 완료되었습니다.")
            }catch(e:NullUserException){
                view.showToastMsg("로그인 되어 있지 않거나, 에러가 발생하였습니다.")
            }catch(e:Exception){
                view.showErrorToast()
            }finally {
                view.hideProgressBar()
            }
        }
    }

}