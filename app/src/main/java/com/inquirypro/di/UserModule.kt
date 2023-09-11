package com.inquirypro.di

import com.inquirypro.repository.UserRepository
import com.inquirypro.repository.UserRepositoryImpl
import com.inquirypro.ui.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userModuleRepo = module { single<UserRepository> { UserRepositoryImpl() } }

val userModule = module { viewModel { UserViewModel(get()) } }