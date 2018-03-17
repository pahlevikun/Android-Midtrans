package com.midtrans.chatapp.presenter.impls

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.util.Log
import com.midtrans.chatapp.R
import com.midtrans.chatapp.etc.config.APIConfig
import com.midtrans.chatapp.presenter.interfaces.SplashInterface
import com.midtrans.chatapp.view.ui.ChatRoomActivity

/**
 * Created by farhan on 3/16/18.
 */
class SplashPresenter : SplashInterface {

    //This is for call check permission, it will return true or false after checking hasPermission
    override fun checkPermission(activity: Activity, PERMISSION: Array<String>) : Boolean {
        val returnResult: Boolean
        if (Build.VERSION.SDK_INT >= 23) {
            Log.d("${APIConfig.TAG} SPLASH", "IN IF Build.VERSION.SDK_INT >= 23")

            returnResult = if (!hasPermissions(activity, *PERMISSION)) {
                Log.d("${APIConfig.TAG} SPLASH", "IN IF hasPermissions")
                ActivityCompat.requestPermissions(activity, PERMISSION,
                        APIConfig.REQUEST_PERMISSION)
                false
            } else {
                Log.d("${APIConfig.TAG} SPLASH", "IN ELSE hasPermissions")
                true
            }
        } else {
            Log.d("${APIConfig.TAG} SPLASH", "IN ELSE  Build.VERSION.SDK_INT >= 23")
            returnResult = true
        }
        return returnResult
    }

    //This is for receiving checking permission, it will return true or false
    override fun resultPermission(activity: Activity, requestCode: Int, grantResults: IntArray)
            : Boolean {
        var returnResult = false
        when (requestCode) {
            APIConfig.REQUEST_PERMISSION -> {
                returnResult = if (grantResults.isNotEmpty() && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    Log.d(APIConfig.TAG, "PERMISSIONS grant")
                    true
                } else {
                    Log.d(APIConfig.TAG, "PERMISSIONS Denied")
                    false
                }
            }
        }
        return returnResult
    }

    //This is for checking permission, it will return true or false
    private fun hasPermissions(context: Context?, vararg permissions: String): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null) {
            permissions
                    .filter {
                        ActivityCompat.checkSelfPermission(context, it) !=
                                PackageManager.PERMISSION_GRANTED
                    }
                    .forEach { return false }
        }
        return true
    }
}
