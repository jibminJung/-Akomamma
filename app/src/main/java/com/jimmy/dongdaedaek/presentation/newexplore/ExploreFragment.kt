package com.jimmy.dongdaedaek.presentation.newexplore

import android.util.Log
import com.google.android.material.chip.Chip
import com.jimmy.dongdaedaek.R
import com.jimmy.dongdaedaek.databinding.FragmentExploreBinding
import com.jimmy.dongdaedaek.presentation.BaseFragment
import com.jimmy.dongdaedaek.presentation.explore.ExploreAdapter
import com.jimmy.dongdaedaek.presentation.explore.RecentReviewAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class ExploreFragment : BaseFragment<ExploreViewModel, FragmentExploreBinding>() {

    override val viewModel by viewModel<ExploreViewModel>()

    override fun getViewBinding(): FragmentExploreBinding =
        FragmentExploreBinding.inflate(layoutInflater)

    val reviewAdapter by lazy {
        RecentReviewAdapter().apply {
            onClickListener = {
                //리뷰 클릭 시 싱당 상세로 이동
            }
        }
    }

    val storeAdapter by lazy {
        ExploreAdapter().apply {
            onClickListener = {
                //식당 클릭 시 식당 상세로 이동
            }
        }
    }

    override fun observeData() {
        viewModel.storeListLiveData.observe(viewLifecycleOwner) {
            storeAdapter.addItem(it)
            storeAdapter.notifyDataSetChanged()
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
    }

    companion object {
        private const val TAG = "ExploreFragment"
    }
}