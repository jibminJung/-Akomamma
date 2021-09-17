package com.jimmy.dongdaedaek.domain.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Review(
    @DocumentId
    val id:String?=null,
    val storeId:String?=null,
    val reviewText:String?=null,

    @ServerTimestamp
    val createdAt: Date? = null,
    val rating:Float?=null,
    val userId:String?=null,
    val photos:List<String>?=null
):Parcelable
