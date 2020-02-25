package br.eloibrito.com.movie_db.utils

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import br.eloibrito.com.movie_db.R
import java.util.*


object CheckReadPermission {
    var status = true
    var REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124

    fun show(activity: AppCompatActivity): Boolean {

        try {
            val permissionsNeeded = ArrayList<String>()

            val permissionsList = ArrayList<String>()
            if (!addPermission(
                    permissionsList,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    activity
                )
            )
                permissionsNeeded.add(activity.resources.getString(R.string.permission_sd_card_escrita))
            if (!addPermission(
                    permissionsList,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    activity
                )
            )


            if (permissionsList.size > 0) {

                if (Build.VERSION.SDK_INT >= 23) {
                    activity.requestPermissions(
                        permissionsList.toTypedArray(),
                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS
                    )
                    return false
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return true
    }


    fun addPermission(
        permissionsList: MutableList<String>,
        permission: String,
        activity: AppCompatActivity
    ): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission)
                // Check for Rationale Option
                if (!activity.shouldShowRequestPermissionRationale(permission))
                    return false
            }
        }
        return true
    }



}