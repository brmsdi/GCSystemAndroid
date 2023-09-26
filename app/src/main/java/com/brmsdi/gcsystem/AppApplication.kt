package com.brmsdi.gcsystem

import android.app.Application
import com.brmsdi.gcsystem.di.AppModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            modules(
                AppModules
            )
        }
    }
}