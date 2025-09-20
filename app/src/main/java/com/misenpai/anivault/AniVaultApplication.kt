package com.misenpai.anivault

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AniVaultApplication : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}

