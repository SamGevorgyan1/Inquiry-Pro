package com.inquirypro.di

import com.inquirypro.repository.PartRepository
import com.inquirypro.repository.PartRepositoryImpl
import com.inquirypro.ui.viewmodel.PartViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val partModuleRepo = module { single<PartRepository>{PartRepositoryImpl()} }

val partModule = module { viewModel { PartViewModel(get()) } }