package com.example.ktortesting

import android.app.Application
import com.example.ktortesting.koin.httpModule
import org.koin.core.context.startKoin

class CkenkenApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                httpModule,
            )
        }
    }
}