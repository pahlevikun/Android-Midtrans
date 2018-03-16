package com.midtrans.chatapp.presenter.interfaces

import android.app.Activity

/**
 * Created by farhan on 3/16/18.
 */
interface SplashInterface {
    fun checkPermission(activity: Activity, PERMISSION: Array<String>)

    fun resultPermission(activity: Activity, requestCode: Int, grantResults: IntArray)
}