package com.jimmy.dongdaedaek.presentation.selectLocation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jimmy.dongdaedaek.BuildConfig
import com.jimmy.dongdaedaek.R
import com.jimmy.dongdaedaek.databinding.FragmentSelectLocationBinding
import com.jimmy.dongdaedaek.extension.setNavigationResult
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import org.koin.android.scope.ScopeFragment

class SelectLocationFragment() : ScopeFragment(), SelectLocationContract.View,OnMapReadyCallback {
    override val presenter: SelectLocationContract.Presenter by inject()

    val naverMapSdk: NaverMapSdk by inject()

    var binding: FragmentSelectLocationBinding? = null
    var naverMap:NaverMap? =null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSelectLocationBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMap()
        bindView()



        presenter.onViewCreated()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    fun bindView(){
        binding?.confirmAddressButton?.setOnClickListener {

            setNavigationResult(binding?.confirmAddressButton?.text.toString().trimStart('.'),"address")
            setNavigationResult(naverMap?.cameraPosition?.target!!,"latlng")
            findNavController().navigateUp()
        }

    }
    fun initMap(){
        naverMapSdk.client =
            NaverMapSdk.NaverCloudPlatformClient(BuildConfig.NAVER_API_KEY)

        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment, it).commit()
            }

        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(naverMap: NaverMap) {
        Log.d("Map..", "onMapReady called...")
        this.naverMap = naverMap
        val donggukPosition = CameraPosition(LatLng(37.55824528668089, 127.00020138425833), 14.5)
        val cameraUpdate = CameraUpdate.toCameraPosition(donggukPosition)
            .animate(CameraAnimation.Easing)
        naverMap.moveCamera(cameraUpdate)

        naverMap.addOnCameraIdleListener {
            Log.d("findName","camera idle...")
            val location = naverMap.cameraPosition.target
            presenter.getAddressByLatlng(location)
        }
    }

    override fun updateButtonName(name: String) {
        binding?.confirmAddressButton?.text = "...$name"
    }

    override fun showProgressOnButton() {
        binding?.confirmAddressButton?.text = "주소 가져오는 중..."
    }

    override fun hideProgressOnButton() {

    }
}