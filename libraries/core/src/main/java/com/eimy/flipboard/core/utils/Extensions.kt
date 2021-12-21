package com.eimy.flipboard.core.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Color
import android.net.Uri
import android.provider.Settings
import android.util.Base64.DEFAULT
import android.util.Base64.decode
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.eimy.flipboard.core.R
import java.util.*

/**
 * Converts a DP value to PX
 */
fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

/**
 * Converts a PX value to DP
 */
fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

inline fun <reified T : Any> tag(): String = T::class.java.simpleName

fun Context.color(@ColorRes resId: Int) = ResourcesCompat.getColor(resources, resId, theme)
fun Fragment.color(@ColorRes resId: Int) = context?.color(resId) ?: Color.TRANSPARENT
fun View.color(@ColorRes resId: Int) = context.color(resId)

fun Context.showToast(message: String, length: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message, length).show()

@SuppressLint("HardwareIds")
fun Context.getUniqueId() = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

fun Fragment.showToast(message: String, length: Int = Toast.LENGTH_SHORT) =
    context?.showToast(message, length)

fun Context.hasPermission(permission: String) =
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

fun Fragment.hasPermission(permission: String) =
    context?.hasPermission(permission) ?: false

fun Char.toDecimal(): Int = Integer.parseInt(this.toString(), 16)


fun Char.isDigit(): Boolean {
    try {
        val num = Integer.parseInt(this.toString())
    } catch (e: NumberFormatException) {
        return false
    }
    return true
}

fun getCurrentTime(): Float {

    val now = Calendar.getInstance().get(Calendar.SECOND)


    var sec: Float = now.toFloat()

    sec = sec / 2

    return sec.toFloat()

}

fun getCurrentSecond(): Int {

    //  val now = S.now()


    return Calendar.getInstance().get(Calendar.SECOND)

}

fun getCurrentTimeStamp(): Long {

    return System.currentTimeMillis()

}

fun String.share(activity: FragmentActivity) =
    ShareCompat.IntentBuilder
        .from(activity)
        .setText(this)
        .setType(ClipDescription.MIMETYPE_TEXT_PLAIN)
        .startChooser()

fun Activity.hideSystemUI() {
    window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN)
}

fun String.getRawResId(res: Resources, packageName: String?): Int =
    res.getIdentifier(this, "raw", packageName)

/**
 * Closes the keyboard only if has been opened by this view and clears the focus from the view.
 */
fun View.closeKeyboard() {
    clearFocus()
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    inputMethodManager?.hideSoftInputFromWindow(windowToken, 0)
}

/**
 * Shows the keyboard on this view and requests focus.
 */
fun View.showKeyboard() {
    post {
        requestFocus()
        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}

/**
 * Checks if the microphone permission has been granted
 */
fun Context.grantedMicPermission(): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.RECORD_AUDIO
    ) == PackageManager.PERMISSION_GRANTED
}

/**
 * Decodes a Base64 String
 */
fun String.decodeFromBase64(): String {
    return decode(this, DEFAULT).toString(charset("UTF-8"))
}