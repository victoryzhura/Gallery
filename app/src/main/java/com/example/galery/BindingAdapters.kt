package com.example.galery

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.galery.data.entity.Urls

@BindingAdapter("imageUrl")
fun ImageView.bindImage(imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(this)
            .load(imgUri)
            .into(this)

    }
}

@BindingAdapter("imageUrlDetails")
fun ImageView.bindImageDetails(imgUrl: Urls) {
    imgUrl.let {

        val imgUriFull = it.full?.toUri()?.buildUpon()?.scheme("https")?.build()

        Glide.with(this)
            .load(imgUriFull)
            .into(this)
    }
}


