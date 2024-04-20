package com.ap.demo

import android.content.SharedPreferences
import androidx.databinding.ktx.BuildConfig
import com.ap.demo.network.base.NetworkService
import com.ap.demo.utils.PrefManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : android.app.Application() {

    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) { }
        instance = this
        setupServices()
    }
    companion object {
        private var instance: App? = null

        @JvmStatic
        fun getInstance(): App? {
            if (instance == null) {
                instance = App()
            }
            return instance
        }
    }

    private fun setupServices() {
        NetworkService.instance.initializeWithApplicationContext(this)

        sharedPreferences= PrefManager.init()
    }

}

