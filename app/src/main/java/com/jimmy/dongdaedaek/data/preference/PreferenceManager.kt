package com.jimmy.dongdaedaek.data.preference

interface PreferenceManager {
    fun getString(key:String):String?
    fun putString(key:String,value:String)

}