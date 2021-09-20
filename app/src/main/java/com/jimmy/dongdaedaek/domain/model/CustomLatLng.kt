package com.jimmy.dongdaedaek.domain.model

import android.os.Parcelable
import com.naver.maps.geometry.LatLng
import kotlinx.parcelize.Parcelize

@Parcelize
data class CustomLatLng(
    val lat : Double?,
    val lng : Double?
):Parcelable {
    constructor() : this(null,null)

    fun toNaverLatlng():LatLng? = if(this.lat !=null && this.lng != null){
        LatLng(this.lat,this.lng)
    }else{
        null
    }
}