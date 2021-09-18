package com.jimmy.dongdaedaek.presentation.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.jimmy.dongdaedaek.R
import com.jimmy.dongdaedaek.databinding.FragmentExploreBinding
import com.jimmy.dongdaedaek.domain.model.Review
import com.jimmy.dongdaedaek.domain.model.Store
import com.jimmy.dongdaedaek.extension.toGone
import com.jimmy.dongdaedaek.extension.toVisible
import org.koin.android.scope.ScopeFragment


class ExploreFragment : ScopeFragment(), ExploreContract.View {

    private var binding: FragmentExploreBinding? = null
    override val presenter: ExploreContract.Presenter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentExploreBinding.inflate(inflater, container, false)
        .also { binding = it }
        .root

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        bindView()

        presenter.onViewCreated()

    }

    override fun onStart() {
        super.onStart()
        checkLoginIntent()
    }

    private fun checkLoginIntent() {
        activity?.intent?.data?.let {
            presenter.checkLogin(requireActivity().intent.data.toString())
            requireActivity().intent.data = null
        }
    }

    private fun initView() {
        val adapter = ExploreAdapter()
        binding?.exploreRecyclerView?.adapter = adapter
        binding?.exploreRecyclerView?.layoutManager = LinearLayoutManager(
            this.context,
            RecyclerView.VERTICAL, false
        )
    }

    override fun initViewPager(recentReviews: List<Review>) {
        val pagerAdapter = RecentReviewAdapter(recentReviews)
        binding?.viewPagerView?.adapter = pagerAdapter.apply {
            onClickListener = {
                presenter.getStoreById(it.storeId!!)
            }
        }
        binding?.viewPagerView?.setCurrentItem(pagerAdapter.itemCount / 2, false)
    }

    override fun initCategory(categories: List<Pair<String, String>>) {
        categories.forEach {
            binding?.categoryChipGroup?.addView(Chip(context).apply {
                text = it.second
                isCheckable = true
                tag = it.first
                setOnCheckedChangeListener{ _, _ ->
                    binding?.categoryChipGroup?.checkedChipIds?.let { idList ->
                        presenter.fetchFilteredStore(
                            idList.map {
                                (binding?.categoryChipGroup?.findViewById(it) as Chip).text
                            } as MutableList<String>
                        )
                    }
                }
            })
        }

    }

    fun bindView() {
        (binding?.exploreRecyclerView?.adapter as ExploreAdapter).onClickListener = { store ->
            val action = ExploreFragmentDirections.toStoreInformationAction(store)
            findNavController().navigate(action)
        }

        binding?.addStoreFab?.setOnClickListener {
            findNavController().navigate(R.id.to_add_store_action)
        }
    }

    override fun showStores(stores: List<Store>) {
        (binding?.exploreRecyclerView?.adapter as ExploreAdapter).run {
            addItem(stores)
            notifyDataSetChanged()
        }
    }

    override fun goToStore(store: Store) {
        findNavController().navigate(ExploreFragmentDirections.toStoreInformationAction(store))
    }

    override fun showProgressBar() {
        binding?.progressView?.toVisible()
    }

    override fun hideProgressBar() {
        binding?.progressView?.toGone()
    }

    override fun showLoginSuccessToast() {
        Toast.makeText(context, "로그인되었습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun showErrorToast() {
        Toast.makeText(context, "예상치 못한 에러가 발생했습니다.", Toast.LENGTH_SHORT).show()
    }
}