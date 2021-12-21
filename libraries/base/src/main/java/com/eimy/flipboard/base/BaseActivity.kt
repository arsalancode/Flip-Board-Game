package com.eimy.flipboard.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.eimy.flipboard.core.utils.hideSystemUI

abstract class BaseActivity : AppCompatActivity() {

    val connectivityManager by lazy {
        getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemUI()
        window.decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                hideSystemUI()
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI()
    }

    private var baseResultData: Intent? = null

    /**
     * This is called for every result added to the activity to be consumed.
     * @return true if the result has been consumed (and will then not be propagated to other activities) or false if not.
     */
    open fun useReturnData(resultData: Result<Parcelable>): Boolean {
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode == Result.REQUEST_CODE) {
            val data = resultData ?: Intent()
            data.extras?.keySet()?.forEach {
                val result = data.extras?.get(it)
                if (result is Result<*>) {
                    if (useReturnData(result)) {
                        data.extras?.remove(it)
                    }
                }
            }
            this.baseResultData = data
        }
    }

    fun addResult(result: Result<Parcelable>) {
        baseResultData = (baseResultData ?: Intent()).apply {
            putExtra(result.key, result)
        }
        setResult(Activity.RESULT_OK, baseResultData)
    }

    override fun startActivity(intent: Intent?) {
        super.startActivityForResult(intent, Result.REQUEST_CODE)
    }

    override fun startActivity(intent: Intent?, options: Bundle?) {
        super.startActivityForResult(intent, Result.REQUEST_CODE, options)
    }

}