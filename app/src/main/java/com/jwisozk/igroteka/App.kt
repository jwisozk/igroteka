package com.jwisozk.igroteka

import android.app.Application
import com.jwisozk.igroteka.di.AppContainer

class App : Application() {
    val appContainer = AppContainer()
}