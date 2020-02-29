package com.nivelais.supinfo.jporating.presentation

import android.app.Application
import com.nivelais.supinfo.jporating.presentation.di.objectboxModule
import com.nivelais.supinfo.jporating.presentation.di.repositoryModule
import com.nivelais.supinfo.jporating.presentation.di.useCasesModule
import com.nivelais.supinfo.jporating.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class JpoReportingApplication : Application() {

    override fun onCreate(){
        super.onCreate()

        // Init koin
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@JpoReportingApplication)
            modules(listOf(repositoryModule, objectboxModule, useCasesModule, viewModelModule))
        }
    }
}