package com.jimmy.dongdaedaek.presentation.addStore

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.iterator
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.jimmy.dongdaedaek.R
import com.jimmy.dongdaedaek.databinding.FragmentAddStoreBinding
import com.jimmy.dongdaedaek.domain.model.Store
import com.jimmy.dongdaedaek.extension.getNavigationResult
import com.jimmy.dongdaedaek.extension.toGone
import com.jimmy.dongdaedaek.extension.toVisible
import com.naver.maps.geometry.LatLng
import org.koin.android.scope.ScopeFragment


class AddStoreFragment : ScopeFragment(), AddStoreContract.View {
    override val presenter: AddStoreContract.Presenter by inject()

    var binding: FragmentAddStoreBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAddStoreBinding.inflate(inflater, container, false)
        .also { fragmentAddStoreBinding ->
            binding = fragmentAddStoreBinding
            getNavigationResult("address")?.let {
                binding?.storeAddressTextView?.text = it.toString()
            }
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindView()
        presenter.onViewCreated()
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }


    private fun bindView() {
        binding?.mapSearchButton?.setOnClickListener {
            findNavController().navigate(R.id.to_select_location)
        }
        binding?.storeNameEditText?.setOnFocusChangeListener { view, b ->
            if (!b) {
                Log.d("hide keyboard", "EditText UnFocused")
                val imm: InputMethodManager =
                    context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        binding?.addTagEditTextView?.apply {
            setOnFocusChangeListener { view, b ->
                if (!b) {
                    Log.d("hide keyboard", "EditText UnFocused")
                    val imm: InputMethodManager =
                        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
            }

            setOnEditorActionListener { v, actionId, _ ->
                return@setOnEditorActionListener when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        binding?.categoryChipGroup?.addView(Chip(context).apply {
                            text = v.editableText.toString()
                            isCheckable = true
                            isChecked = true
                        },2)
                        v.text = ""
                        v.clearFocus()
                        true
                    }
                    else -> false
                }
            }
        }
        binding?.root?.setOnClickListener {
            binding?.storeNameEditText?.clearFocus()
            binding?.addTagEditTextView?.clearFocus()
        }

        binding?.registerStoreButton?.setOnClickListener {

            val name = binding?.storeNameEditText?.text.toString()
            if (name == "") {
                makeToast("가게명을 입력해주세요")
                return@setOnClickListener
            }
            val address = getNavigationResult("address").toString()
            val latlng = getNavigationResult("latlng") as? LatLng
            if (latlng == null) {
                makeToast("위치를 지정해주세요")
                return@setOnClickListener
            }
            Log.d("register..", "button clicked null pass")
            val seletedCategoryName: MutableList<String> = mutableListOf()
            val newCategoryName: MutableList<String> = mutableListOf()
            binding?.categoryChipGroup?.iterator()?.forEach {
                val chip = it as? Chip
                if (chip?.isChecked == true) {
                    seletedCategoryName.add(chip.text.toString())
                    if(chip.tag == null){
                        newCategoryName.add(chip.text.toString())
                    }
                    Log.d("register..", "categoryIds ${it.tag?:"null tag"}")
                }
            }
            if (seletedCategoryName.isEmpty()) {
                makeToast("태그를 지정해주세요 (여러개)")
            }

            presenter.registerNewCategory(newCategoryName)
            presenter.registerStore(name, address, latlng, seletedCategoryName)

        }
    }

    override fun initCategory(categories: List<Pair<String, String>>) {
        categories.forEach {
            binding?.categoryChipGroup?.addView(Chip(context).apply {
                text = it.second
                isCheckable = true
                tag = it.first
                setTextAppearanceResource(R.style.ChipStyleNew)
            })
        }

    }

    override fun makeToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showProgressBar() {
        binding?.progressView?.toVisible()
    }

    override fun hideProgressBar() {
        binding?.progressView?.toGone()
    }

    override fun navigateToStore(store: Store) {
        findNavController().navigate(
            AddStoreFragmentDirections.toStoreInformationActionInclusive(
                store
            )
        )
    }
}