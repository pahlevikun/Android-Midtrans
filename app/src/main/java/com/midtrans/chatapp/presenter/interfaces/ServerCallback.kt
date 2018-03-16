package com.midtrans.chatapp.presenter.interfaces

/**
 * Created by farhan on 3/16/18.
 */
interface ServerCallback {
    fun onSuccess(response: String)

    fun onFailed(response: String)

    fun onFailure(throwable: Throwable)
}