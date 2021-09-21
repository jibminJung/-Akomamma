package com.jimmy.dongdaedaek.presentation.addStore

import com.jimmy.dongdaedaek.NullUserException
import com.jimmy.dongdaedaek.data.repository.CategoryRepository
import com.jimmy.dongdaedaek.domain.usecase.RegisterCategoryUseCase
import com.jimmy.dongdaedaek.domain.usecase.RegisterStoreUseCase
import com.naver.maps.geometry.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class AddStorePresenter(
    val view: AddStoreContract.View,
    private val categoryRepository: CategoryRepository,
    val registerStoreUseCase: RegisterStoreUseCase,
    val registerCategory: RegisterCategoryUseCase
) : AddStoreContract.Presenter {
    override val scope: CoroutineScope = MainScope()
    var initCategoryList : List<Pair<String,String>>? = null

    override fun onViewCreated() {
        loadCategories()
    }

    override fun onDestroyView() {

    }

    private fun loadCategories() {
        scope.launch {
            try {
                val cats = categoryRepository.loadCategories()
                initCategoryList = cats
                view.initCategory(cats)
            } catch (e: Exception) {
                view.makeToast("알 수 없는 오류가 발생했습니다.")
            } finally {
                view.hideProgressBar()
            }
        }
    }

    override fun registerStore(
        name: String,
        address: String,
        latlng: LatLng,
        categoryIds: MutableList<String>
    ) {
        scope.launch {
            try {
                view.showProgressBar()
                val registeredStore = registerStoreUseCase(name, address, categoryIds, latlng)
                view.navigateToStore(registeredStore)
            } catch (e: NullUserException) {
                view.makeToast("로그인 후 등록 가능합니다.")
            } catch (e: Exception) {
                view.makeToast("알 수 없는 오류가 발생했습니다.")
            } finally {
                view.hideProgressBar()
            }
        }
    }

    override fun registerNewCategory(newCategoryName: MutableList<String>) {
        scope.launch {
            try {
                view.showProgressBar()
                registerCategory(newCategoryName)
            } catch (e: NullUserException) {
                view.makeToast("로그인 후 등록 가능합니다.")
            } catch (e: Exception) {
                view.makeToast("알 수 없는 오류가 발생했습니다.")
            } finally {
                view.hideProgressBar()
            }
        }
    }
}