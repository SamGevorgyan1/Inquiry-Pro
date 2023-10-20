package com.inquirypro

import android.app.Application
import com.google.firebase.FirebaseApp
import com.inquirypro.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@App)
            androidLogger(Level.DEBUG)
            modules(
                registrationModule, registrationRepoModule,
                loginModule, loginModuleRepo,
                logoutModule, logoutRepoModule,
                resetPasswordModule, resetPasswordRepoModule,
                categoryModule, categoryModuleRepo,
                partModule, partModuleRepo,
                questionModule, questionModuleRepo,
                historyModule, historyModuleRepo,
                questionResultModule, questionResultModuleRepo,
                articleModule, articleRepoModule,
                sharedViewModel
            )
        }
    }
}