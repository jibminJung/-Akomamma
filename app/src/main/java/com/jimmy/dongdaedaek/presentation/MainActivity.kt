package com.jimmy.dongdaedaek.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.jimmy.dongdaedaek.BuildConfig
import com.jimmy.dongdaedaek.R
import com.jimmy.dongdaedaek.databinding.ActivityMainBinding
import com.naver.maps.map.NaverMapSdk

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val navigationController by lazy{
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment).navController
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Dongdaedaek)
        setContentView(binding.root)

        initView()
        bindView()
        NaverMapSdk.getInstance(applicationContext).client = NaverMapSdk.NaverCloudPlatformClient(BuildConfig.NAVER_API_KEY)


    }

    fun initView(){
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_explore, R.id.navigation_map, R.id.navigation_my_page
//            )
//        )
        binding.navView.setupWithNavController(navigationController)
    }
    fun bindView(){
//        navigationController.addOnDestinationChangedListener { _, destination, _ ->
//            if(destination.id == R.id.navigation_store_information) {
//
//                binding.navView.visibility = View.GONE
//            } else {
//
//                binding.navView.visibility = View.VISIBLE
//            }
//        }
    }
}