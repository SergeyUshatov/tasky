package com.sergius.tasky

import android.app.Application
import com.sergius.agenda.presentation.di.agendaModule
import com.sergius.auth.data.di.authDataModule
import com.sergius.auth.presentation.di.authViewModelModule
import com.sergius.core.data.di.coreDataModule
import com.sergius.core.database.di.databaseModule
import com.sergius.tasky.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class TaskyApp: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@TaskyApp)
            modules(
                appModule,
                databaseModule,
                coreDataModule,
                authDataModule,
                authViewModelModule,
                agendaModule,
            )
        }
    }
}