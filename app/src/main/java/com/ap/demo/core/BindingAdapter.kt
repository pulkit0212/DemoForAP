package com.ap.demo.core

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ap.demo.R


@BindingAdapter("android:imageUrl")
fun imageSrc(imageView: ImageView, resource: String?) {
    Glide.with(imageView.context).load(R.drawable.placeholder).into(imageView)
    resource?.let {
        if (it != "")
            Glide.with(imageView.context).load(resource).error(R.drawable.placeholder).into(imageView)
        else
            Glide.with(imageView.context).load(R.drawable.placeholder).into(imageView)
    }
}


