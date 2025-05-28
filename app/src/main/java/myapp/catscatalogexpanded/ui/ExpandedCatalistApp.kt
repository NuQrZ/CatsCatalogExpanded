package myapp.catscatalogexpanded.ui

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ExpandedCatalistApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("Test", "App:onCreate")
    }
}