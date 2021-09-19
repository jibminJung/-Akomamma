package com.jimmy.dongdaedaek.data.repository

import com.jimmy.dongdaedaek.data.api.TmapApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class TmapRepository(
    private val tmapApi: TmapApi,
    val dispatcher: CoroutineDispatcher
) {

    suspend fun getLocationName(lat: Double, lon: Double): String? = withContext(dispatcher) {
       val location = tmapApi.getLocationName(lat, lon)
            .body()
           ?.addressInfo
        location?.fullAddress
    }
}