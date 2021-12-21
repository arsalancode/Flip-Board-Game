package com.eimy.flipboard.core

import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.TouchDelegate
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.eimy.flipboard.core.lists.DataBoundModel
import com.eimy.flipboard.core.lists.DataBoundRecyclerAdapter
import com.eimy.flipboard.core.utils.color


@BindingAdapter("android:src")
fun ImageView.setImageViewResource(@DrawableRes resource: Int?) {
    if (resource != null) {
        setImageResource(resource)
    }
}

@BindingAdapter(value = ["items", "lifecycleOwner"], requireAll = false)
fun RecyclerView.setItems(items: List<DataBoundModel>?, lifecycleOwner: LifecycleOwner? = null) {
    items?.let {
        adapter = DataBoundRecyclerAdapter(it, lifecycleOwner)
    }
}

@BindingAdapter("android:colorId")
fun TextView.setBackgroundResource(colorId: MutableLiveData<Int>) {
    if (colorId.value == 0) return

    setBackgroundResource(colorId.value!!)
}


@BindingAdapter(value = ["items", "lifecycleOwner", "offscreenRender"], requireAll = false)
fun ViewPager2.setViewPagerItems(
    items: List<DataBoundModel>?,
    lifecycleOwner: LifecycleOwner? = null,
    offscreenRender: Int? = null
) {
    offscreenRender?.let {
        offscreenPageLimit = offscreenRender
    }
    items?.let {
        adapter = DataBoundRecyclerAdapter(it, lifecycleOwner)
    }
}

@BindingAdapter(value = ["imageUrl", "centerCrop", "fitCenter"], requireAll = false)
fun ImageView.setImageUrl(url: String?, centerCrop: Boolean = true, fitCenter: Boolean = false) {
    if (url != null) {
        this.post {
            val requestBuilder = Glide.with(this)
                .load(url)
                .placeholder(ColorDrawable(ContextCompat.getColor(context, R.color.black)))
                .error(ColorDrawable(ContextCompat.getColor(context, R.color.black)))
                .transition(DrawableTransitionOptions.withCrossFade(300))
            if (centerCrop) {
                requestBuilder.centerCrop()
            }
            if (fitCenter) {
                requestBuilder.fitCenter()
            }
            requestBuilder.into(this)
        }
    }
}

@BindingAdapter("onTouchListener")
fun View.setOnTouchListener(touchListener: View.OnTouchListener?) {
    this.setOnTouchListener(touchListener)
}

@BindingAdapter("selected")
fun View.setIsSelected(isSelected: Boolean) {
    this.isSelected = isSelected
}

@BindingAdapter("app:lottie_rawRes")
fun LottieAnimationView.setAnimation(@RawRes resource: Int?) {
    if (resource != null)
        this.setAnimation(resource)
}

@BindingAdapter("increaseTouch")
fun increaseTouch(view: View, value: Float) {
    val parent = view.parent
    (parent as View).post {
        val rect = Rect()
        view.getHitRect(rect)
        val intValue = value.toInt()
        rect.top -= intValue    // increase top hit area
        rect.left -= intValue   // increase left hit area
        rect.bottom += intValue // increase bottom hit area
        rect.right += intValue  // increase right hit area
        parent.touchDelegate = TouchDelegate(rect, view);
    }
}