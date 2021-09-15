package com.jimmy.dongdaedaek.presentation.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.navigation.fragment.navArgs
import com.jimmy.dongdaedaek.BuildConfig
import com.jimmy.dongdaedaek.R
import com.jimmy.dongdaedaek.databinding.FragmentMapPageBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import org.koin.android.scope.ScopeFragment

class MapPageFragment : ScopeFragment(), MapPageContract.View, OnMapReadyCallback {
    override val presenter: MapPageContract.Presenter by inject()
    val naverMapSdk: NaverMapSdk by inject()
    var binding: FragmentMapPageBinding? = null
    val argument:MapPageFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMapPageBinding.inflate(inflater, container, false)
        .also {
            binding = it
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMap()
        presenter.onViewCreated()
    }

    private fun initMap() {
        naverMapSdk.client = NaverMapSdk.NaverCloudPlatformClient(BuildConfig.NAVER_API_KEY)
        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        Log.d("Map..", "onMapReady called...")
        naverMap.isIndoorEnabled = true
        val donggukPosition = CameraPosition(LatLng(37.558162, 127.000345), 14.5)
        val cameraUpdate = CameraUpdate.toCameraPosition(donggukPosition)
            .animate(CameraAnimation.Easing)
        naverMap.moveCamera(cameraUpdate)

        Log.d("Map..", "${argument.store}")
        argument.store?.latLng?.let{customLatlng->

            Log.d("Map..", "let..")
            customLatlng.toNaverLatlng()?.let{
                val storePosition = CameraPosition(it, 16.5)
                val cameraUpdatePos = CameraUpdate.toCameraPosition(storePosition)
                    .animate(CameraAnimation.Easing)
                naverMap.moveCamera(cameraUpdatePos)
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroyView()
    }


}