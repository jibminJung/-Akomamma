package com.jimmy.dongdaedaek.presentation.newexplore

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jimmy.dongdaedaek.data.repository.CategoryRepository
import com.jimmy.dongdaedaek.domain.model.Review
import com.jimmy.dongdaedaek.domain.model.Store
import com.jimmy.dongdaedaek.domain.usecase.GetFilteredStoreUseCase
import com.jimmy.dongdaedaek.domain.usecase.GetRecentReviewUseCase
import com.jimmy.dongdaedaek.domain.usecase.GetStoresUseCase
import com.jimmy.dongdaedaek.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ExploreViewModel(
    val getStoresUseCase: GetStoresUseCase,
    val categoryRepository: CategoryRepository,
    val getFilteredStoreUseCase: GetFilteredStoreUseCase,
    val getRecentReview: GetRecentReviewUseCase
) : BaseViewModel() {

    var checkedId : MutableLiveData<List<String>> = MutableLiveData<List<String>>()

    var filter: MutableList<String> = mutableListOf()

    private var _categoryListLiveData = MutableLiveData<List<Pair<String, String>>>()
    val categoryLiveData: LiveData<List<Pair<String, String>>>
        get() = _categoryListLiveData

    private var _storeListLiveData = MutableLiveData<List<Store>>()
    val storeListLiveData: LiveData<List<Store>>
        get() = _storeListLiveData

    private var _recentReviewListLiveData = MutableLiveData<List<Review>>()
    val recentReviewListLiveData : LiveData<List<Review>>
    get() = _recentReviewListLiveData

    override fun fetchData(): Job = viewModelScope.launch {
        Log.d(TAG, "fetchData: fetching data...")
        if (filter.isEmpty()) {
            val stores = getStoresUseCase()
            _storeListLiveData.value = stores
        } else {
            val stores = getFilteredStoreUseCase(filter)
            _storeListLiveData.value = stores
        }
        if (categoryLiveData.value.isNullOrEmpty()) {
            val category = categoryRepository.loadCategories()
            _categoryListLiveData.value = category
        }
        val recentReviews = getRecentReview()
        _recentReviewListLiveData.value = recentReviews
    }

    fun setFilterCondition(filter: MutableList<String>,checkedIds:MutableList<String>) {
        this.filter = filter
        checkedId.value = checkedIds
        fetchData()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared: ")
    }
    

    companion object {
        private const val TAG = "ExploreViewModel"
    }
}