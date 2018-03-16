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
import com.beehapps.eddokter.system.config.APIConfig
import com.midtrans.chatapp.R
import com.midtrans.chatapp.presenter.interfaces.SplashInterface
import com.midtrans.chatapp.view.ui.MainActivity

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

    override fun checkPermission(activity: Activity, PERMISSION: Array<String>) {
        if (Build.VERSION.SDK_INT >= 23) {
            Log.d("${APIConfig.TAG} SPLASH", "IN IF Build.VERSION.SDK_INT >= 23")

            if (!hasPermissions(activity, *PERMISSION)) {
                Log.d("${APIConfig.TAG} SPLASH", "IN IF hasPermissions")
                ActivityCompat.requestPermissions(activity, PERMISSION,
                        APIConfig.REQUEST_PERMISSION)
            } else {
                Log.d("${APIConfig.TAG} SPLASH", "IN ELSE hasPermissions")
                splashLanding(activity)
            }
        } else {
            Log.d("${APIConfig.TAG} SPLASH", "IN ELSE  Build.VERSION.SDK_INT >= 23")
            splashLanding(activity)
        }
    }

    override fun resultPermission(activity: Activity, requestCode: Int, grantResults: IntArray) {
        when (requestCode) {
            APIConfig.REQUEST_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    Log.d(APIConfig.TAG, "PERMISSIONS grant")
                    splashLanding(activity)
                } else {
                    Log.d(APIConfig.TAG, "PERMISSIONS Denied")
                    val alert = android.support.v7.app.AlertDialog.Builder(activity)
                    alert.setTitle(activity.getString(R.string.alert_title_warning))
                    alert.setMessage(activity.getString(R.string.alert_body_permission))
                    alert.setCancelable(false)
                    alert.setPositiveButton(activity.getString(R.string.alter_button_permission))
                    { _, _ ->
                        // TODO Auto-generated method stub
                        activity.finish()
                        activity.startActivity(activity.intent)
                    }
                    alert.show()
                }
            }
        }
    }

    private fun splashLanding(activity: Activity) {
        Handler().postDelayed(object : Thread() {
            override fun run() {
                val intent = Intent(activity, MainActivity::class.java)
                activity.startActivity(intent)
                activity.finish()
            }
        }, 2500)
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
