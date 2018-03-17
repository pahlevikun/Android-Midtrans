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

    val isOnline: Boolean
        get() {
            try {
                val p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com")
                val returnVal = p1.waitFor()
                return (returnVal == 0)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return false
        }

    private fun isNetworkAvailable(activity: Activity): Boolean {
        val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

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

    override fun resultPermission(activity: Activity, requestCode: Int, grantResults: IntArray)
            : Boolean {
        var returnResult = false
        when (requestCode) {
            APIConfig.REQUEST_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    Log.d(APIConfig.TAG, "PERMISSIONS grant")
                    returnResult = true
                } else {
                    Log.d(APIConfig.TAG, "PERMISSIONS Denied")
                    returnResult = false
                }
            }
        }
        return returnResult
    }

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
