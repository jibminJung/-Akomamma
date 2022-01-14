package com.jimmy.dongdaedaek.presentation.newexplore

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.jimmy.dongdaedaek.R
import com.jimmy.dongdaedaek.databinding.FragmentExploreBinding
import com.jimmy.dongdaedaek.domain.usecase.GetStoreByIdUseCase
import com.jimmy.dongdaedaek.presentation.BaseFragment
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ExploreFragment : BaseFragment<ExploreViewModel, FragmentExploreBinding>() {

    override val viewModel by viewModel<ExploreViewModel>()

    val getStoreById:GetStoreByIdUseCase by inject<GetStoreByIdUseCase>()

    override fun getViewBinding(): FragmentExploreBinding =
        FragmentExploreBinding.inflate(layoutInflater)


    val reviewAdapter by lazy {
        RecentReviewAdapter().apply {
            onClickListener = {
                //리뷰 클릭 시 식당 상세로 이동
                MainScope().launch {
                    val store = getStoreById(it.storeId!!)
                    findNavController().navigate(ExploreFragmentDirections.toStoreInformationAction(store))
                }

            }
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
        }
    }

    val storeAdapter by lazy {
        ExploreAdapter().apply {
            onClickListener = { store ->
                //식당 클릭 시 식당 상세로 이동
                val action = ExploreFragmentDirections.toStoreInformationAction(store)
                findNavController().navigate(action)
            }
        }

    }

    override fun observeData() {
        viewModel.storeListLiveData.observe(viewLifecycleOwner) {
            storeAdapter.differ.submitList(it)
            Log.d(TAG, "observeData: adding item ${it.size}")
        }
        viewModel.categoryLiveData.observe(viewLifecycleOwner) { categories ->
            binding?.categoryChipGroup?.clearCheck()
            categories.forEach {
                binding?.categoryChipGroup?.addView(Chip(context).apply {
                    text = it.second
                    isCheckable = true
                    tag = it.first
                    elevation = 2F
                    setTextAppearanceResource(R.style.ChipStyleNew)
                    setChipBackgroundColorResource(R.color.white)
                    setOnCheckedChangeListener { _, _ ->
                        binding?.categoryChipGroup?.checkedChipIds?.let { idList ->
                            viewModel.setFilterCondition(
                                idList.map {
                                    (binding?.categoryChipGroup?.findViewById(it) as Chip).text
                                } as MutableList<String>, idList.map {
                                    (binding?.categoryChipGroup?.findViewById(it) as Chip).tag
                                } as MutableList<String>
                            )
                        }
                    }
                })
            }

        }
        viewModel.checkedId.observe(viewLifecycleOwner) {
            it.forEach { id ->
                binding?.categoryChipGroup?.findViewWithTag<Chip>(id)?.isChecked = true
            }
        }

        viewModel.recentReviewListLiveData.observe(viewLifecycleOwner) {
            reviewAdapter.data = it
            reviewAdapter.notifyDataSetChanged()
            binding?.viewPagerView?.setCurrentItem(reviewAdapter.itemCount / 2, false)
        }

    }

    override fun initViews() = with(binding) {
        exploreRecyclerView.adapter = storeAdapter
        viewPagerView.adapter = reviewAdapter
        addStoreFab.setOnClickListener {
            findNavController().navigate(R.id.to_add_store_action)
        }
    }


    companion object {
        private const val TAG = "ExploreFragment"
    }
}