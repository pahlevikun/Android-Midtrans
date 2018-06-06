package com.midtrans.chatapp.presenter.interfaces

import android.app.Activity
import com.midtrans.chatapp.model.ChatGson
import com.midtrans.chatapp.model.pojo.Chat

/**
 * Created by farhan on 3/16/18.
 */
interface ChatRoomInterface {
    fun getChat(callback: ServerCallback)

    fun parsingChat(response: String, context: Activity): ChatGson

    fun isOnline() : Boolean

    fun convertTime(oldTime:String) : String
}