package com.nicopaul.mankuro

import android.app.Application

class MankuroApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ThemePrefs.apply(ThemePrefs.isDark(this))
    }
}