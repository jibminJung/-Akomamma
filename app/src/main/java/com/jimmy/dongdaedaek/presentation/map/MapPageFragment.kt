package com.jimmy.dongdaedaek.presentation.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import com.jimmy.dongdaedaek.R
import com.jimmy.dongdaedaek.databinding.FragmentMapPageBinding
import com.jimmy.dongdaedaek.domain.model.Store
import com.jimmy.dongdaedaek.extension.toGone
import com.jimmy.dongdaedaek.extension.toVisible
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.MarkerIcons
import org.koin.android.scope.ScopeFragment

class MapPageFragment : ScopeFragment(), MapPageContract.View, OnMapReadyCallback {
    override val presenter: MapPageContract.Presenter by inject()
    private var binding: FragmentMapPageBinding? = null
    private val argument: MapPageFragmentArgs by navArgs()
    private var naverMap: NaverMap? = null
    private val markerList: MutableList<Marker> = mutableListOf()
    private val infoWindow: InfoWindow by lazy {
        initInfoWindow()
    }


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
        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap

        Log.d("Map..", "onMapReady called...")
        naverMap.apply {
            isIndoorEnabled = true
            setOnMapClickListener { _, _ ->
                infoWindow.close()
            }
        }
        val donggukPosition = CameraPosition(LatLng(37.558162, 127.000345), 14.5)
        val cameraUpdate = CameraUpdate.toCameraPosition(donggukPosition)
            .animate(CameraAnimation.Easing)
        naverMap.moveCamera(cameraUpdate)

        Log.d("Map..", "${argument.store}")
        moveToArgumentStore(naverMap)
    }


    private fun moveToArgumentStore(naverMap: NaverMap) {
        argument.store?.latLng?.let { customLatlng ->
            Log.d("Map..", "let..")
            customLatlng.toNaverLatlng()?.let {
                val storePosition = CameraPosition(it, 16.5)
                val cameraUpdatePos = CameraUpdate.toCameraPosition(storePosition)
                    .animate(CameraAnimation.Easing)
                naverMap.moveCamera(cameraUpdatePos)
            }

        }
    }

    override fun initCategory(categories: List<Pair<String, String>>) {
        categories.forEach {
            binding?.categoryChipGroup?.addView(Chip(context).apply {
                text = it.second
                isCheckable = true
                tag = it.first
                setOnCheckedChangeListener(onCheckedListener)
            })
        }
    }

    private val onCheckedListener = CompoundButton.OnCheckedChangeListener { _, _ ->
        binding?.categoryChipGroup?.checkedChipIds?.let { idList ->
            presenter.fetchFilteredStore(
                idList.map {
                    (binding?.categoryChipGroup?.findViewById(it) as Chip).text
                } as MutableList<String>
            )
        }
    }


    override fun showStoresMark(stores: List<Store>) {
        markerList.forEach {
            it.map = null
        }
        markerList.clear()
        if (stores.isEmpty()) {
            showToastMsg("표시할 위치가 없습니다.")
        } else {
            stores.forEach { store ->
                markerList.add(
                    Marker().apply {
                        position = store.latLng?.toNaverLatlng()!!
                        icon = MarkerIcons.BLACK
                        iconTintColor = resources.getColor(R.color.orange)
                        width = 72
                        height = 86
                        captionText = store.title!!
                        subCaptionText = store.rating?.take(4)!!
                        tag = store
                        isHideCollidedCaptions = true
                        isHideCollidedSymbols = true
                        map = naverMap
                        setOnClickListener { overlay ->
                            val mark = overlay as Marker
                            if (mark.infoWindow == null) {
                                this@MapPageFragment.infoWindow.open(mark)
                            } else {
                                this@MapPageFragment.infoWindow.close()
                            }
                            true
                        }
                    }
                )

            }
        }
    }

    private fun initInfoWindow(): InfoWindow =
        InfoWindow().apply {
            adapter = object : InfoWindow.DefaultTextAdapter(requireContext()) {
                override fun getText(infoWindow: InfoWindow): CharSequence {
                    return infoWindow.marker?.captionText + " > "
                }
            }
            setOnClickListener {
                findNavController().navigate(
                    MapPageFragmentDirections.toStoreInformationAction(((it as InfoWindow).marker?.tag as Store))
                )
                true
            }
        }

    override fun showProgressBar() {
        binding?.loadingView?.toVisible()
    }

    override fun showErrorToast() {
        Toast.makeText(this.context, "예기치 못한 에러가 발생했습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun hideProgressBar() {
        binding?.loadingView?.toGone()
    }

    override fun showToastMsg(msg: String) {
        Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show()
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