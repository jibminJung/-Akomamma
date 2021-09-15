package com.jimmy.dongdaedaek.domain.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import com.naver.maps.geometry.LatLng
import kotlinx.parcelize.Parcelize

@Parcelize
data class Store(
    @DocumentId
    val id:String?=null,
    val title:String?=null,
    val address:String?=null,
    val category:List<String>?=null,
    val rating:String?=null,
    val numberOfReview:Int?=null,
    val latLng: CustomLatLng?=null
):Parcelable
