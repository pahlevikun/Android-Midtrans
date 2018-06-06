package com.midtrans.chatapp.view.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.midtrans.chatapp.R
import com.midtrans.chatapp.presenter.impls.SplashPresenter

/**
 * Created by farhan on 3/16/18.
 */

class SplashActivity : AppCompatActivity() {

    //Variable for checking in onResume
    private var resume = false
    //Declare presenter
    private var presenter = SplashPresenter()
    //PERMISIION
    private val permissionArray = arrayOf(android.Manifest.permission.INTERNET,
            android.Manifest.permission.ACCESS_NETWORK_STATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //Make condition for checking permission, if return true go to passingSplashScreen()
        if (presenter.checkPermission(this, permissionArray)){
            passingSplashScreen()
        }
    }

    public override fun onResume() {
        super.onResume()
        //resume use for prevent check permission in first time
        if (resume) {
            if(presenter.checkPermission(this, permissionArray)){
                passingSplashScreen()
            }
        }
        resume = true
    }

    private fun passingSplashScreen(){
        //Delay 2.5 s before go to ChatRoomActivity
        Handler().postDelayed(object : Thread() {
            override fun run() {
                val intent = Intent(this@SplashActivity, ChatRoomActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 2500)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //Same as before, check result if true go to passingSplashScreen(), else give permission
        if (presenter.resultPermission(this, requestCode, grantResults)){
            passingSplashScreen()
        }else{
            val alert = android.support.v7.app.AlertDialog.Builder(this)
            alert.setTitle(getString(R.string.alert_title_warning))
            alert.setMessage(getString(R.string.alert_body_permission))
            alert.setCancelable(false)
            alert.setPositiveButton(getString(R.string.alter_button_permission)) { _, _ ->
                // TODO Auto-generated method stub
                finish()
                startActivity(intent)
            }
            alert.show()
        }
    }

    override fun onBackPressed() {
        //prevent user for close the app when checking permission
    }
}
