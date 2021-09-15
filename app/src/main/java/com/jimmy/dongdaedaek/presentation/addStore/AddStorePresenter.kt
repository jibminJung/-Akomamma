package com.jimmy.dongdaedaek.presentation.addStore

import android.widget.Toast
import com.jimmy.dongdaedaek.NullUserException
import com.jimmy.dongdaedaek.data.repository.CategoryRepository
import com.jimmy.dongdaedaek.domain.usecase.RegisterStoreUseCase
import com.naver.maps.geometry.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class AddStorePresenter(
    val view: AddStoreContract.View,
    val categoryRepository: CategoryRepository,
    val registerStoreUseCase: RegisterStoreUseCase
) : AddStoreContract.Presenter {
    override val scope: CoroutineScope = MainScope()

    override fun onViewCreated() {
        loadCategories()
    }

    override fun onDestroyView() {

    }

    fun loadCategories() {
        scope.launch {
            try {
                val cats = categoryRepository.loadCategories()
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
                registerStoreUseCase(name, address, categoryIds, latlng)
                view.navigateUp()
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