package com.jimmy.dongdaedaek.data.repository

import android.util.Log
import com.jimmy.dongdaedaek.data.api.TmapApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class TmapRepository(
    val tmapApi: TmapApi,
    val dispatcher: CoroutineDispatcher
) {

    suspend fun getLocationName(lat: Double, lon: Double): String? = withContext(dispatcher) {

        val info = tmapApi.getLocationName(lat, lon).body()
            ?.addressInfo

        return@withContext info?.fullAddress
    }
}


