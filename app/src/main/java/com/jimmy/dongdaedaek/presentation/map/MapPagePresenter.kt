package com.jimmy.dongdaedaek.presentation.map

import com.naver.maps.map.NaverMapSdk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class MapPagePresenter(
    val view: MapPageContract.View
) :MapPageContract.Presenter {
    override val scope : CoroutineScope = MainScope()
    override fun onViewCreated() {
    }

    override fun onDestroyView() {

    }
}