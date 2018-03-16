package com.midtrans.chatapp.view.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.midtrans.chatapp.R
import com.midtrans.chatapp.presenter.impls.SplashPresenter

class SplashActivity : AppCompatActivity() {

    private var resume = false
    private var presenter = SplashPresenter()

    private val permissionArray = arrayOf(android.Manifest.permission.INTERNET,
            android.Manifest.permission.ACCESS_NETWORK_STATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        presenter.checkPermission(this, permissionArray)
    }

    public override fun onResume() {
        super.onResume()
        if (resume) {
            presenter.checkPermission(this, permissionArray)
        }
        resume = true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        presenter.resultPermission(this, requestCode, grantResults)
    }

    override fun onBackPressed() {}
}
