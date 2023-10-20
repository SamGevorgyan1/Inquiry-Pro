package com.inquirypro.di

import com.inquirypro.repository.QuestionResultRepository
import com.inquirypro.repository.QuestionResultRepositoryImpl
import com.inquirypro.ui.viewmodel.QuestionResultViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val questionResultModuleRepo = module { single<QuestionResultRepository> { QuestionResultRepositoryImpl() } }

val questionResultModule = module { viewModel{ QuestionResultViewModel(get()) } }