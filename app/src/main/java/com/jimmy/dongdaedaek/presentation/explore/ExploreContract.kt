package com.jimmy.dongdaedaek.presentation.explore

import android.net.Uri
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
    }

    interface Presenter:BasePresenter{
        fun checkLogin(uri: String)
    }
}