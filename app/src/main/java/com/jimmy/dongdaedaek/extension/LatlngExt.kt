package com.jimmy.dongdaedaek.extension

import com.google.type.LatLng

fun LatLng.toNaverLatLng():com.naver.maps.geometry.LatLng{
    return com.naver.maps.geometry.LatLng(this.latitude,this.longitude)
}