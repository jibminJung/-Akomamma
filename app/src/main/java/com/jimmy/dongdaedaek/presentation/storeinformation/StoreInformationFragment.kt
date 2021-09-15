package com.jimmy.dongdaedaek.presentation.storeinformation

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.jimmy.dongdaedaek.databinding.FragmentStoreInformationBinding
import com.jimmy.dongdaedaek.domain.model.Review
import com.jimmy.dongdaedaek.domain.model.Store
import com.jimmy.dongdaedaek.extension.toGone
import com.jimmy.dongdaedaek.extension.toVisible
import org.koin.android.scope.ScopeFragment
import org.koin.core.component.bind
import org.koin.core.parameter.parametersOf

class StoreInformationFragment:ScopeFragment(),StoreInformationContract.View {
    override val presenter: StoreInformationContract.Presenter by inject { parametersOf(arguments.store)}
    val arguments:StoreInformationFragmentArgs by navArgs()
    private var binding: FragmentStoreInformationBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentStoreInformationBinding.inflate(inflater, container, false)
        .also { binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bindView()
        presenter.onViewCreated()
    }



    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showStoreInfo(store:Store){
        binding?.storeNameTextView?.text = store.title
        //binding?.storeDescriptionTextView?.text = store.category

    }
    fun initView(){
        binding?.storeInformationRecyclerView?.adapter = StoreInformationAdapter()
        binding?.storeInformationRecyclerView?.layoutManager = LinearLayoutManager(context)

    }
    fun bindView(){
        (binding?.storeInformationRecyclerView?.adapter as StoreInformationAdapter)
            .onSubmitButtonClickListener={content,rating->
            presenter.requestSubmitReview(content,rating)

            hideKeyboard()
        }

        binding?.findMapButton?.setOnClickListener {
            val action = StoreInformationFragmentDirections.toStoreLocation(arguments.store)
            findNavController().navigate(action)
        }
    }
    override fun addReviewData(reviews:List<Review>){
        (binding?.storeInformationRecyclerView?.adapter as StoreInformationAdapter).apply {
            addReviewData(reviews)
            notifyDataSetChanged()
        }

    }

    override fun showProgressBar() {
        binding?.progressView?.toVisible()
    }

    override fun hideProgressBar() {
        binding?.progressView?.toGone()
    }
    fun hideKeyboard() {
        val inputMethodManager = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
    }

    override fun showErrorToast() {
        Toast.makeText(context,"에러가 발생하였습니다. 잠시 후 다시 시도해주세요.",Toast.LENGTH_SHORT).show()
    }

    override fun refreshReviewData(reviews:List<Review>) {
        (binding?.storeInformationRecyclerView?.adapter as StoreInformationAdapter).apply{
            clearReviewData()
            addReviewData(reviews)
            notifyDataSetChanged()
        }
    }
}