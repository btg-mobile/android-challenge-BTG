package com.rafaelpereiraramos.challengebtg.commonsandroid

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity

class ViewUtils {

    companion object {
        fun hideSoftKeyboard(activity: FragmentActivity?) {
            val activityView = activity?.window?.decorView?.rootView
            activityView?.let {
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(it.windowToken, 0)
            }
        }
    }
}