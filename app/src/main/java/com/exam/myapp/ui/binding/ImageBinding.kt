package com.exam.myapp.ui.binding

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.exam.myapp.R

object ImageBinding {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(R.mipmap.pokemon_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("imageBackgroundUrl")
    fun loadImageAndSetBackground(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .asBitmap()
            .load(url)
            .placeholder(R.mipmap.pokemon_placeholder2)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                    imageView.setImageResource(R.mipmap.pokemon_placeholder2)
                    imageView.setBackgroundResource(R.color.separator)
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                    return false
                }

            })
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val palette = Palette.from(resource).generate()
                    var color = palette.getDominantColor(Color.BLACK)
                    if(!ViewBinding.isColorLight(color)) {
                        color = Color.parseColor("#ffdd80")
                    }

                    // Set the dominant color as the background color of the ImageView
                    imageView.setImageBitmap(resource)
                    imageView.setBackgroundColor(color)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // Handle the case where the image load is cleared
                }

            })

    }

}