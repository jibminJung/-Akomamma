package com.jimmy.dongdaedaek

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.jimmy.dongdaedaek.databinding.ActivityFontLicenseBinding

class FontLicenseActivity:AppCompatActivity() {
    val binding : ActivityFontLicenseBinding by lazy{
        ActivityFontLicenseBinding.inflate(LayoutInflater.from(this))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val bufferedReader = resources.openRawResource(R.raw.font_license).bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        binding.textView.text = inputString
    }

}