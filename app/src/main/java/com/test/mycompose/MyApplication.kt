package com.test.mycompose

import android.app.Application

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        splitInit(this)
    }
}