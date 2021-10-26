package com.example.trippleatt

import android.app.Application
import com.example.trippleatt.data.Repository
import com.example.trippleatt.ui.DataViewModel
import com.example.trippleatt.ui.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class Application : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@Application))

        bind() from singleton { AppPreferences(instance()) }
        bind() from singleton { Repository(instance()) }
        bind() from singleton { DataViewModel(instance()) }

        bind() from singleton { ViewModelFactory(instance()) }
    }
}