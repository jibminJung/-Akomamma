package com.jimmy.dongdaedaek.data.api

import com.jimmy.dongdaedaek.BuildConfig
import com.jimmy.dongdaedaek.data.response.AddressResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TmapApi {

    @GET("tmap/geo/reversegeocoding?version=1&addressType=A03&appKey=${BuildConfig.TMAP_API_KEY}")
    suspend fun getLocationName(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ):Response<AddressResponse>


}