package com.logistics.logix

import android.app.Application
import com.logistics.logix.di.netModule
import com.logistics.logix.di.preferencesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Logix  : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@Logix)
            androidFileProperties()
            modules(listOf(  preferencesModule, netModule))
        }
    }

}