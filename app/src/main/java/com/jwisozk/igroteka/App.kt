package com.jwisozk.igroteka

import android.app.Application
import com.jwisozk.igroteka.di.AppContainer

class App : Application() {

    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(resources)
    }
}