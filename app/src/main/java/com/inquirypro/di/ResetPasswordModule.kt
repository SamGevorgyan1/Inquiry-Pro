package com.inquirypro.di


import com.inquirypro.repository.ResetPasswordImpl
import com.inquirypro.repository.ResetPasswordRepository
import com.inquirypro.ui.auth.viewmodel.ResetPasswordViewModel
import com.inquirypro.ui.viewmodel.SharedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val resetPasswordRepoModule = module { single<ResetPasswordRepository> { ResetPasswordImpl() } }

val resetPasswordModule = module { viewModel { ResetPasswordViewModel(get()) } }

val sharedViewModel = module { viewModel { SharedViewModel() } }