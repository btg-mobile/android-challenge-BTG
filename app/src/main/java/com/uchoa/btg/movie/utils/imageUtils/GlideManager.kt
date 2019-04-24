package com.uchoa.btg.movie.utils.imageUtils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.uchoa.btg.movie.R
import com.uchoa.btg.movie.utils.Constants

class GlideManager {

    companion object {

        fun loadImage(context: Context, imageView: ImageView, imagePath: String) {
            Glide.with(context)
                .load(Constants.IMAGE_BASE_URL + imagePath)
                .apply(RequestOptions.placeholderOf(R.color.black_transparent_00))
                .into(imageView)
        }

        fun loadImage(context: Context, imageView: ImageView, imagePath: String, placeHolder: Drawable) {
            Glide.with(context)
                .load(Constants.IMAGE_BASE_URL + imagePath)
                .apply(RequestOptions.placeholderOf(placeHolder))
                .into(imageView)
        }

    }
}