package com.test.mycompose

import android.app.Application
import com.test.mycompose.split.initSplitRules

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initSplitRules(this)
    }
}