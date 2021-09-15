package com.jimmy.dongdaedaek.domain.usecase

import com.jimmy.dongdaedaek.data.repository.TmapRepository

class FindLocationUseCase(val tmapRepository: TmapRepository) {

    suspend operator fun invoke(lat:Double,lon:Double):String?=tmapRepository.getLocationName(lat,lon)
}