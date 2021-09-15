package com.jimmy.dongdaedaek.data.response


import com.google.gson.annotations.SerializedName

data class AddressResponse(
    @SerializedName("addressInfo")
    val addressInfo: AddressInfo?
)