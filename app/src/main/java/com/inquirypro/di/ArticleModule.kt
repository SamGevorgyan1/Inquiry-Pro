package com.inquirypro.di

import com.inquirypro.repository.ArticleRepository
import com.inquirypro.repository.ArticleRepositoryImpl
import com.inquirypro.ui.viewmodel.ArticleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val articleRepoModule = module { single<ArticleRepository> { ArticleRepositoryImpl() } }

val articleModule = module { viewModel { ArticleViewModel(get()) } }

