package com.exam.myapp.ui.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter

object TextViewBinding {

    @JvmStatic
    @BindingAdapter("android:text")
    fun setText(view: TextView, value: Int) {
        view.text = value.toString()
    }
}