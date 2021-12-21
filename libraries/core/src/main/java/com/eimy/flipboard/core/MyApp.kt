package com.eimy.flipboard.core

import android.app.Application
import com.feliperrm.utils.init

class MyApp : Application() {

    companion object {
        var instance: MyApp? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        init()
    }

}