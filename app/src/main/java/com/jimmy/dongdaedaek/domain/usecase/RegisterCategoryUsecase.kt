package com.jimmy.dongdaedaek.domain.usecase

import com.jimmy.dongdaedaek.data.repository.CategoryRepository

class RegisterCategoryUseCase (private val categoryRepository: CategoryRepository){
    suspend operator fun invoke(newCategoryName:List<String>){
        categoryRepository.registerCategories(newCategoryName)
    }
}