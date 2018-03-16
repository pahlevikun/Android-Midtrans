package com.midtrans.chatapp.presenter.interfaces

import com.midtrans.chatapp.model.pojo.Chat

/**
 * Created by farhan on 3/16/18.
 */
interface ChatRoomInterface {
    fun getChat(callback: ServerCallback)

    fun parsingChat(response: String): ArrayList<Chat>
}