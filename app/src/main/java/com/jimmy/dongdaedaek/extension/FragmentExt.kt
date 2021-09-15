package com.jimmy.dongdaedaek.extension

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.getNavigationResult(key:String="result")=
    findNavController().currentBackStackEntry?.savedStateHandle?.get<Any>(key)

fun Fragment.setNavigationResult(result: Any, key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}

