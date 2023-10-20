package com.inquirypro.di

import com.inquirypro.repository.LoginRepository
import com.inquirypro.repository.LoginRepositoryImpl
import com.inquirypro.ui.auth.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModuleRepo = module { single<LoginRepository> { LoginRepositoryImpl() } }

val loginModule = module { viewModel { LoginViewModel(get()) } }