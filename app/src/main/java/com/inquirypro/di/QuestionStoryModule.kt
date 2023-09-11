package com.inquirypro.di

import com.inquirypro.repository.QuestionStoryRepository
import com.inquirypro.repository.QuestionStoryRepositoryImpl
import com.inquirypro.ui.viewmodel.QuestionStoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val questionStoryModuleRepo =
    module { single<QuestionStoryRepository> { QuestionStoryRepositoryImpl() } }

val questionStoryModule = module { viewModel{ QuestionStoryViewModel(get()) } }