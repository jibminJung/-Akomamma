package com.jimmy.dongdaedaek.extension

import com.naver.maps.geometry.LatLng
import java.text.DecimalFormat

fun Float.toDecimalFormatString(format: String): String = DecimalFormat(format).format(this)