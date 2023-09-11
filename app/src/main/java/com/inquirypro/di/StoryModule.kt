package com.inquirypro.di

import com.inquirypro.repository.StoryRepository
import com.inquirypro.repository.StoryRepositoryImpl
import com.inquirypro.ui.viewmodel.StoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val storyModuleRepo = module { single<StoryRepository> { StoryRepositoryImpl() } }

val storyModule = module { viewModel { StoryViewModel(get()) } }