package com.jimmy.dongdaedaek.presentation.selectLocation

import com.jimmy.dongdaedaek.presentation.BasePresenter
import com.jimmy.dongdaedaek.presentation.BaseView
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.Tm128
import com.naver.maps.geometry.WebMercatorCoord

interface SelectLocationContract {
    interface View : BaseView<Presenter> {
        fun updateButtonName(name: String)
        fun showProgressOnButton()
        fun hideProgressOnButton()
    }

    interface Presenter : BasePresenter {
        fun getAddressByLatlng(latlng: LatLng)

    }
}