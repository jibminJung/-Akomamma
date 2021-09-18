package com.jimmy.dongdaedaek.presentation.map

import com.jimmy.dongdaedaek.domain.model.Store
import com.jimmy.dongdaedaek.presentation.BasePresenter
import com.jimmy.dongdaedaek.presentation.BaseView

interface MapPageContract {
    interface View:BaseView<Presenter>{
        fun initCategory(categories:List<Pair<String,String>>)
        fun showStoresMark(stores: List<Store>)
        fun showProgressBar()
        fun showErrorToast()
        fun showToastMsg(msg:String)
        fun hideProgressBar()
    }

    interface Presenter:BasePresenter {
        fun fetchFilteredStore(checkedChip: MutableList<String>)
        fun loadCategories()

    }
}