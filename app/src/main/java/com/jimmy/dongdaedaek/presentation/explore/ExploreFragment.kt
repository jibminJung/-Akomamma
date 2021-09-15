package com.jimmy.dongdaedaek.presentation.explore

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jimmy.dongdaedaek.R
import com.jimmy.dongdaedaek.databinding.FragmentExploreBinding
import com.jimmy.dongdaedaek.domain.model.Store
import com.jimmy.dongdaedaek.extension.toGone
import com.jimmy.dongdaedaek.extension.toVisible
import org.koin.android.scope.ScopeFragment
import kotlin.concurrent.fixedRateTimer


class ExploreFragment : ScopeFragment(),ExploreContract.View{

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

        val pagerAdapter = RecentStoreAdapter()
        binding?.viewPagerView?.adapter = pagerAdapter
    }

    fun bindView(){
        (binding?.exploreRecyclerView?.adapter as ExploreAdapter).onClickListener={store->
            val action = ExploreFragmentDirections.toStoreInformationAction(store)
            findNavController().navigate(action)
        }

        binding?.addStoreFab?.setOnClickListener {
            findNavController().navigate(R.id.to_add_store_action)
        }
    }

    override fun showStores(stores: List<Store>) {
        (binding?.viewPagerView?.adapter as RecentStoreAdapter).run{
            addItem(stores)
            notifyDataSetChanged()
            Log.d("storeId",stores.first().id?:"there is no id")
        }

        (binding?.exploreRecyclerView?.adapter as ExploreAdapter).run {
            addItem(stores)
            notifyDataSetChanged()
        }

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