package com.inquirypro.di

import com.inquirypro.repository.CategoryRepository
import com.inquirypro.repository.CategoryRepositoryImpl
import com.inquirypro.ui.viewmodel.CategoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val categoryModuleRepo = module { single<CategoryRepository> { CategoryRepositoryImpl() } }

val categoryModule = module { viewModel { CategoryViewModel(get()) } }