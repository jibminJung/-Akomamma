package com.jimmy.dongdaedaek.presentation.addStore

import com.jimmy.dongdaedaek.presentation.BasePresenter
import com.jimmy.dongdaedaek.presentation.BaseView
import com.naver.maps.geometry.LatLng

interface AddStoreContract {
    interface View:BaseView<Presenter>{
        fun initCategory(categories:List<Pair<String,String>>)
        fun showProgressBar()
        fun hideProgressBar()
        fun makeToast(msg:String)
        fun navigateUp()
    }

    interface Presenter:BasePresenter{
        fun registerStore(name:String,address:String,latlng:LatLng,categoryIds:MutableList<String>)
    }
}