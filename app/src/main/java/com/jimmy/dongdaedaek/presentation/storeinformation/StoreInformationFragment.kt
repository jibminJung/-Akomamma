package com.jimmy.dongdaedaek.presentation.storeinformation

import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.jimmy.dongdaedaek.databinding.FragmentStoreInformationBinding
import com.jimmy.dongdaedaek.domain.model.Review
import com.jimmy.dongdaedaek.domain.model.Store
import com.jimmy.dongdaedaek.extension.toGone
import com.jimmy.dongdaedaek.extension.toVisible
import org.koin.android.scope.ScopeFragment
import org.koin.core.parameter.parametersOf

class StoreInformationFragment : ScopeFragment(), StoreInformationContract.View {
    override val presenter: StoreInformationContract.Presenter by inject { parametersOf(arguments.store) }
    val arguments: StoreInformationFragmentArgs by navArgs()
    private var binding: FragmentStoreInformationBinding? = null
    var imageRecyclerAdapter:ImageRecyclerAdapter? = null
    val getContent =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { list: List<Uri> ->
            // Callback function.. Handle the returned Uri
            if (list.isNotEmpty()) {
                (binding?.reviewForm?.thumbRecyclerView?.adapter as? ImageRecyclerAdapter)?.addData(list)
            }

        }

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

    override fun showStoreInfo(store: Store) {
        binding?.storeNameTextView?.text = store.title
        //binding?.storeDescriptionTextView?.text = store.category

    }

    fun initView() {
        binding?.storeInformationRecyclerView?.adapter = StoreInformationAdapter()
        binding?.storeInformationRecyclerView?.layoutManager = LinearLayoutManager(context)
        binding?.reviewForm?.thumbRecyclerView?.adapter = ImageRecyclerAdapter().also {
            imageRecyclerAdapter = it
        }


    }

    fun bindView() {

        binding?.findMapButton?.setOnClickListener {
            val action = StoreInformationFragmentDirections.toStoreLocation(arguments.store)
            findNavController().navigate(action)
        }

        binding?.reviewForm?.let { reviewForm ->
            reviewForm.submitButton.setOnClickListener {
                presenter.requestSubmitReview(
                    reviewForm.reviewTextEditText.text.toString(),
                    reviewForm.ratingBar.rating,
                    imageRecyclerAdapter?.data
                )
                hideKeyboard()
                reviewForm.reviewTextEditText.text.clear()
            }
            reviewForm.reviewTextEditText.addTextChangedListener { editable ->
                reviewForm.submitButton.isEnabled = (editable?.length ?: 0 > 3)
            }
            reviewForm.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
                reviewForm.ratingScoreTextView.text = rating.toString()
            }
            reviewForm.imageAddButton.setOnClickListener {
                //check permission for storage and get Content
                checkExternalStoragePermission()
            }

        }
    }

    override fun clearImageInput() {
        imageRecyclerAdapter?.data?.clear()
        imageRecyclerAdapter?.notifyDataSetChanged()
    }


    private fun checkExternalStoragePermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                getContent.launch("image/*")
            }
            else -> {
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_REQUEST_CODE ->
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContent.launch("image/*")
                } else {
                    Toast.makeText(context, "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show()
                }
        }
    }


    override fun addReviewData(reviews: List<Review>) {
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
        val inputMethodManager =
            context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
    }

    override fun showErrorToast() {
        Toast.makeText(context, "에러가 발생하였습니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
    }

    override fun showToastMsg(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun refreshReviewData(reviews: List<Review>) {
        (binding?.storeInformationRecyclerView?.adapter as StoreInformationAdapter).apply {
            clearReviewData()
            addReviewData(reviews)
            notifyDataSetChanged()
        }
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 1000
    }
}