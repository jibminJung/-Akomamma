package com.jimmy.dongdaedaek.presentation.storeinformation

import android.net.Uri
import com.jimmy.dongdaedaek.domain.model.Review
import com.jimmy.dongdaedaek.domain.model.Store
import com.jimmy.dongdaedaek.presentation.BasePresenter
import com.jimmy.dongdaedaek.presentation.BaseView

interface StoreInformationContract {
    interface View:BaseView<Presenter>{
        fun showProgressBar()
        fun hideProgressBar()
        fun showToastMsg(msg:String)
        fun showErrorToast()
        fun showStoreInfo(store: Store)
        fun addReviewData(reviews:List<Review>)
        fun refreshReviewData(reviews:List<Review>)
        fun clearImageInput()
    }
    interface Presenter:BasePresenter{
        fun requestSubmitReview(context: String, rating: Float, data: MutableList<Uri>?)
        val store: Store
    }
}