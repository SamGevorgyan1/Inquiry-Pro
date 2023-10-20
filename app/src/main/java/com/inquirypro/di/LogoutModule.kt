package com.inquirypro.di

import com.inquirypro.repository.LogoutRepoImpl
import com.inquirypro.repository.LogoutRepository
import com.inquirypro.ui.viewmodel.LogoutViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val logoutRepoModule = module { single<LogoutRepository> { LogoutRepoImpl() } }

val logoutModule = module { viewModel { LogoutViewModel(get()) } }