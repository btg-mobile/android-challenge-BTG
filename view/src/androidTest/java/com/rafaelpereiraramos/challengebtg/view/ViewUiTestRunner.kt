package com.rafaelpereiraramos.challengebtg.view

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class ViewUiTestRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, ViewTestApplication::class.java.name, context)
    }
}