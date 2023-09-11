package com.inquirypro.di

import com.inquirypro.repository.QuestionRepository
import com.inquirypro.repository.QuestionRepositoryImpl
import com.inquirypro.ui.viewmodel.QuestionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val questionModuleRepo = module { single<QuestionRepository> { QuestionRepositoryImpl() } }

val questionModule = module { viewModel { QuestionViewModel(get()) } }