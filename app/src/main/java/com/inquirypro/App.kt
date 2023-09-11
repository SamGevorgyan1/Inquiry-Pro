package com.inquirypro

import android.app.Application
import com.inquirypro.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger(Level.DEBUG)
            modules(
                userModule, userModuleRepo,
                categoryModule, categoryModuleRepo,
                partModule, partModuleRepo,
                questionModule, questionModuleRepo,
                storyModule, storyModuleRepo,
                questionStoryModule, questionStoryModuleRepo
            )
        }
    }
}