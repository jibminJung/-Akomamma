package com.jimmy.dongdaedaek.presentation.selectLocation

import android.util.Log
import com.jimmy.dongdaedaek.domain.usecase.FindLocationUseCase
import com.naver.maps.geometry.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.internal.wait

class SelectLocationPresenter(
    val view: SelectLocationContract.View,
    val findLocationUseCase: FindLocationUseCase
) : SelectLocationContract.Presenter {
    override val scope: CoroutineScope = MainScope()

    override fun onViewCreated() {
    }

    override fun onDestroyView() {
    }

    override fun getAddressByLatlng(latlng: LatLng) {
        Log.d("findName","presenter function called...")
        scope.launch {
            try {
                view.showProgressOnButton()

                Log.d("findName","katech x:${latlng.latitude} katech.y:${latlng.longitude}")
                val name = findLocationUseCase(latlng.latitude, latlng.longitude)?:"명칭 없음."
                Log.d("findName",name)
                view.updateButtonName(name)
            } catch (e: Exception) {
                Log.d("findName","exception occurred.")
            } finally {

            }
        }

    }
}