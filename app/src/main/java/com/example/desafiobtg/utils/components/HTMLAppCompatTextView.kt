package com.example.desafiobtg.utils.components

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.example.desafiobtg.R
import com.example.desafiobtg.utils.fromHtml

class HTMLAppCompatTextView constructor(context: Context, attrs: AttributeSet? = null): AppCompatTextView(context, attrs) {
    init {
        attrs?.let {
            val arr = context.obtainStyledAttributes(it, R.styleable.HTMLAppCompatTextView, 0, 0)

            if (arr.hasValue(R.styleable.HTMLAppCompatTextView_text)) {
                val blockText = (arr.getString(R.styleable.HTMLAppCompatTextView_text) ?: "").fromHtml()
                text = blockText
            }

            arr.recycle()
        }
    }
}