package com.jimmy.dongdaedaek.presentation.wishstorelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.jimmy.dongdaedaek.databinding.FragmentWishStoreListBinding
import com.jimmy.dongdaedaek.domain.model.Store
import org.koin.android.scope.ScopeFragment

class WishStoreListFragment : ScopeFragment(), WishStoreListContract.View {
    override val presenter: WishStoreListContract.Presenter by inject()
    var binding: FragmentWishStoreListBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentWishStoreListBinding.inflate(inflater, container, false).also {
        binding = it
    }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        presenter.onViewCreated()
    }

    private fun initView() {
        binding?.wishStoreRecyclerView?.adapter = WishStoreListAdapter().apply {
            clickListener = {
                presenter.getStore(it.id!!)
            }
        }
    }

    override fun goToStoreInformation(store:Store){
        findNavController().navigate(WishStoreListFragmentDirections.toStoreInformationAction(store))
    }
    override fun addDataToRecycler(data: List<Store>) {
        (binding?.wishStoreRecyclerView?.adapter as WishStoreListAdapter).addData(data)
    }

    override fun showToastMsg(msg: String) {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
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