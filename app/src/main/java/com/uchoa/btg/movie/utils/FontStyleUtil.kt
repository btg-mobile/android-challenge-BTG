package com.uchoa.btg.movie.utils

import android.content.Context
import android.graphics.Typeface
import android.widget.TextView

object FontStyleUtil {

    fun applyFontStyle(context: Context, view: TextView, fontPath: String) {
        val face = Typeface.createFromAsset(context.assets, fontPath)
        view.typeface = face
    }
}
