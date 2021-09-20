package com.jimmy.dongdaedaek.presentation.storeinformation

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.jimmy.dongdaedaek.NullUserException
import com.jimmy.dongdaedaek.domain.model.Store
import com.jimmy.dongdaedaek.domain.usecase.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class StoreInformationPresenter(
    override val store: Store,
    val view:StoreInformationContract.View,
    val firebaseAuth: FirebaseAuth,
    val getReviewUseCase: GetReviewUseCase,
    val uploadReviewUseCase: UploadReviewUseCase,
    val uploadPhotosUseCase: UploadPhotosUseCase,
    val registerWishStore : RegisterWishStoreUseCase,
    val deleteWishStore:DeleteWishStoreUseCase,
    val checkUserWishStoreUseCase:CheckUserWishStoreUseCase
    ):StoreInformationContract.Presenter {

    override val scope: CoroutineScope = MainScope()

    override fun onViewCreated() {
        view.showStoreInfo(store)
        refreshReviewData(store)
    }

    override fun onDestroyView() {
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
                view.showToastMsg("로그인 후 이용 가능합니다.")
            }catch(e:Exception){
                view.showErrorToast()
            }finally {
                view.hideProgressBar()
            }
        }
    }

    override fun registerWishStore() {
        scope.launch {
            try {
                view.showProgressBar()
                registerWishStore(store)
                view.showToastMsg("관심 목록에 추가되었습니다.")
                view.buttonSelected()
            }catch(e:NullUserException){
                view.showToastMsg("로그인 후 이용 가능합니다.")
            }catch(e:Exception){
                view.showErrorToast()
            }finally {
                view.hideProgressBar()
            }
        }
    }

    override fun deleteWishStore() {
        scope.launch {
            try {
                view.showProgressBar()
                deleteWishStore(store)
                view.buttonReleased()
            }catch(e:NullUserException){
                view.showToastMsg("로그인 후 이용 가능합니다.")
            }catch(e:Exception){
                view.showErrorToast()
            }finally {
                view.hideProgressBar()
            }
        }
    }

    override fun checkUserWishStore() {
        scope.launch {
            try {
                view.showProgressBar()
                val wished = checkUserWishStoreUseCase(store)
                if(wished){
                    view.buttonSelected()
                }else{
                    view.buttonReleased()
                }
            }catch(e:NullUserException){

            }catch(e:Exception){
                view.showErrorToast()
            }finally {
                view.hideProgressBar()
            }
        }
    }
}