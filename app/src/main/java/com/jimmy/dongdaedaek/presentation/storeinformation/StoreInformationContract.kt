package com.jimmy.dongdaedaek.presentation.storeinformation

import com.jimmy.dongdaedaek.domain.model.Review
import com.jimmy.dongdaedaek.domain.model.Store
import com.jimmy.dongdaedaek.presentation.BasePresenter
import com.jimmy.dongdaedaek.presentation.BaseView

interface StoreInformationContract {
    interface View:BaseView<Presenter>{
        fun showProgressBar()
        fun hideProgressBar()
        fun showErrorToast()
        fun showStoreInfo(store: Store)
        fun addReviewData(reviews:List<Review>)
        fun refreshReviewData(reviews:List<Review>)
    }
    interface Presenter:BasePresenter{
        fun requestSubmitReview(context:String,rating:Float)
        val store: Store
    }
}