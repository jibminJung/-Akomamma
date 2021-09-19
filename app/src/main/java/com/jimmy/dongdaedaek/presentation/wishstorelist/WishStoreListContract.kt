package com.jimmy.dongdaedaek.presentation.wishstorelist

import com.jimmy.dongdaedaek.domain.model.Store
import com.jimmy.dongdaedaek.presentation.BasePresenter
import com.jimmy.dongdaedaek.presentation.BaseView

interface WishStoreListContract {
    interface View:BaseView<Presenter>{
        fun showToastMsg(msg:String)
        fun addDataToRecycler(data:List<Store>)
        fun goToStoreInformation(store: Store)
    }

    interface Presenter:BasePresenter{
        fun fetchWishStoreList()
        fun getStore(id:String)

    }
}