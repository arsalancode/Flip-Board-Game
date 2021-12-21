package com.eimy.flipboard.navigation

import android.content.Intent

object AppNavigation {

    fun openHostActivity(): Intent {
        return Intent("action.host.open").addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    }


}