package com.inquirypro.di

import com.inquirypro.repository.RegistrationRepository
import com.inquirypro.repository.RegistrationRepositoryImpl
import com.inquirypro.ui.auth.viewmodel.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val registrationRepoModule = module { single<RegistrationRepository> { RegistrationRepositoryImpl() } }

val registrationModule = module { viewModel { RegistrationViewModel(get()) } }