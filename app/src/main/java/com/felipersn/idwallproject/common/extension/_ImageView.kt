package com.felipersn.idwallproject.common.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions

fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .asBitmap()
        .load(url)
        .into(this)
}

fun ImageView.loadImageWithCrossFade(url: String) {
    Glide.with(this)
        .asBitmap()
        .load(url)
        .transition(BitmapTransitionOptions.withCrossFade())
        .into(this)
}