package com.exam.myapp.ui.binding

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

object ViewBinding {

    @JvmStatic
    @BindingAdapter("progressBarColor")
    fun setProgressBarColor(view: ProgressBar, color: Int) {
        val colorStateList = ColorStateList.valueOf(color)
        view.progressDrawable.setTintList(colorStateList)
    }

    @JvmStatic
    @BindingAdapter("backgroundPalette")
    fun setBackgroundPalette(view: View, url: String?) {
        Glide.with(view.context)
            .asBitmap()
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val palette = Palette.from(resource).generate()
                    var color = palette.getDominantColor(Color.BLACK)
                    if(!isColorLight(color)) {
                        color = Color.parseColor("#ffdd80")
                    }

                    // Set the dominant color as the background color of the ImageView
                    view.setBackgroundColor(color)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // Handle the case where the image load is cleared
                }
            })
    }

    fun isColorLight(color: Int): Boolean {
        // Extract RGB components from the color
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)

        return red > 30 && green > 30 && blue > 30
    }


}