package com.jimmy.dongdaedaek.data.response


import com.google.gson.annotations.SerializedName

data class AddressInfo(
    @SerializedName("addressType")
    val addressType: String?,
    @SerializedName("adminDong")
    val adminDong: String?,
    @SerializedName("adminDongCode")
    val adminDongCode: String?,
    @SerializedName("buildingIndex")
    val buildingIndex: String?,
    @SerializedName("buildingName")
    val buildingName: String?,
    @SerializedName("bunji")
    val bunji: String?,
    @SerializedName("city_do")
    val cityDo: String?,
    @SerializedName("eup_myun")
    val eupMyun: String?,
    @SerializedName("fullAddress")
    val fullAddress: String?,
    @SerializedName("gu_gun")
    val guGun: String?,
    @SerializedName("legalDong")
    val legalDong: String?,
    @SerializedName("legalDongCode")
    val legalDongCode: String?,
    @SerializedName("mappingDistance")
    val mappingDistance: String?,
    @SerializedName("ri")
    val ri: String?,
    @SerializedName("roadCode")
    val roadCode: String?,
    @SerializedName("roadName")
    val roadName: String?
)