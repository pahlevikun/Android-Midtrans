package com.midtrans.chatapp.view.ui

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.midtrans.chatapp.R
import com.midtrans.chatapp.model.pojo.Chat
import com.midtrans.chatapp.presenter.impls.ChatRoomPresenter
import com.midtrans.chatapp.presenter.interfaces.ServerCallback
import com.midtrans.chatapp.view.adapter.ChatRoomAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_chat_room.*

/**
 * Created by farhan on 3/16/18.
 */

class ChatRoomActivity : AppCompatActivity() {

    private val presenter = ChatRoomPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        presenter.getChat(object : ServerCallback {
            override fun onSuccess(response: String) {
                setUpAdapter(presenter.parsingChat(response))
            }

            override fun onFailed(response: String) {
                Snackbar.make(coordinatorLayoutChatRoom, response, Snackbar.LENGTH_SHORT).show()
            }

            override fun onFailure(throwable: Throwable) {
                Snackbar.make(coordinatorLayoutChatRoom, throwable.toString(),
                        Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    private fun setUpAdapter(chatList: ArrayList<Chat>) {
        if (chatList.size != 0) {
            Picasso.with(this).load(chatList[0].avatar).into(imageViewChatRoomAvatar)
        }
        val adapter = ChatRoomAdapter(this, chatList)
        val layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, true)
        layoutManager.stackFromEnd = true
        recyclerViewChatRoom.layoutManager = layoutManager
        recyclerViewChatRoom.isNestedScrollingEnabled = false
        recyclerViewChatRoom.adapter = adapter
        adapter.notifyDataSetChanged()

    }
}
