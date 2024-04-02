package com.example.ktortesting

import android.app.Application
import com.example.ktortesting.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class CkenkenApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CkenkenApplication)
            androidLogger()
            modules(
                AppModule().module,
            )
        }
    }
}