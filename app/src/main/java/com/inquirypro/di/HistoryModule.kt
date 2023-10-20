package com.inquirypro.di

import com.inquirypro.repository.HistoryRepository
import com.inquirypro.repository.HistoryRepositoryImpl
import com.inquirypro.ui.viewmodel.HistoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val historyModuleRepo = module { single<HistoryRepository> { HistoryRepositoryImpl() } }

val historyModule = module { viewModel { HistoryViewModel(get()) } }