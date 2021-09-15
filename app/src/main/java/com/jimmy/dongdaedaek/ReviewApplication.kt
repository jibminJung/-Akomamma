package com.jimmy.dongdaedaek

import android.app.Application
import com.jimmy.dongdaedaek.di.appModule
import com.jimmy.dongdaedaek.di.dataModule
import com.jimmy.dongdaedaek.di.domainModule
import com.jimmy.dongdaedaek.di.presenterModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ReviewApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(
                if (BuildConfig.DEBUG) {
                    Level.ERROR
                } else {
                    Level.NONE
                }
            )
            androidContext(this@ReviewApplication)
            modules(appModule + dataModule+ domainModule+presenterModule)
        }
    }
}