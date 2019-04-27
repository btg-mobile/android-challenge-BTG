package com.example.desafiobtg.utils

object ActivityUtils {
    fun addFragmentToActivity(fragmentManager: android.support.v4.app.FragmentManager,
                              fragment: android.support.v4.app.Fragment, frameId: Int) {
        checkNotNull(fragmentManager)
        checkNotNull(fragment)
        val transaction = fragmentManager.beginTransaction()
        transaction.add(frameId, fragment)
        transaction.commit()
    }
}