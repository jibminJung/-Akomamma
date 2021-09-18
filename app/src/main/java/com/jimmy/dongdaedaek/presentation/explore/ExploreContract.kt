package com.jimmy.dongdaedaek.presentation.explore

import com.jimmy.dongdaedaek.domain.model.Review
import com.jimmy.dongdaedaek.domain.model.Store
import com.jimmy.dongdaedaek.presentation.BasePresenter
import com.jimmy.dongdaedaek.presentation.BaseView

interface ExploreContract {
    interface View:BaseView<BasePresenter>{
        fun showProgressBar()
        fun hideProgressBar()
        fun showStores(stores:List<Store>)
        fun showLoginSuccessToast()
        fun showErrorToast()
        fun initViewPager(recentReviews:List<Review>)
        fun goToStore(store:Store)
        fun initCategory(categories:List<Pair<String,String>>)

    }

    interface Presenter:BasePresenter{
        fun checkLogin(uri: String)
        fun fetchRecentReview()
        fun getStoreById(storeId:String)
        fun fetchFilteredStore(checkedChip: MutableList<String>)
    }
}