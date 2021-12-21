package com.eimy.flipboard.base

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    private val connectivityManager by lazy {
        requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    }

    private var baseResultData: Intent? = null

    /**
     * This is called for every result added to the activity to be consumed.
     * @return true if the result has been consumed (and will then not be propagated to other activities) or false if not.
     */
    open fun useReturnData(resultData: Result<Parcelable>): Boolean {
        return false
    }

    val authSharedPreferences by lazy {
        requireContext().getSharedPreferences(
            requireContext().applicationContext.packageName + "auth_config",
            Context.MODE_PRIVATE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode == Result.REQUEST_CODE) {
            val data = resultData ?: Intent()
            data.extras?.keySet()?.forEach {
                val result = data.extras?.get(it)
                if (result is Result<out Parcelable>) {
                    if (useReturnData(result)) {
                        data.extras?.remove(it)
                    }
                }
            }
            this.baseResultData = data
        }
    }

    fun addResult(result: Result<Parcelable>) {
        val act = activity
        if (act is BaseActivity) {
            act.addResult(result)
        }
    }

    override fun startActivity(intent: Intent?) {
        super.startActivityForResult(intent, Result.REQUEST_CODE)
    }

    override fun startActivity(intent: Intent?, options: Bundle?) {
        super.startActivityForResult(intent, Result.REQUEST_CODE, options)
    }
}