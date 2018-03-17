@file:Suppress("DEPRECATION")

package com.midtrans.chatapp.view.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
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
    private var loading: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        loading = ProgressDialog.show(this, getString(R.string.progress_loading),
                getString(R.string.progress_getting), false, false)
        presenter.getChat(object : ServerCallback {
            override fun onSuccess(response: String) {
                hideDialog()
                setUpAdapter(presenter.parsingChat(response, this@ChatRoomActivity))
            }

            override fun onFailed(response: String) {
                hideDialog()
                Snackbar.make(coordinatorLayoutChatRoom, response, Snackbar.LENGTH_SHORT).show()
                setUpAdapter(presenter.parsingChat(response, this@ChatRoomActivity))
            }

            override fun onFailure(throwable: Throwable) {
                hideDialog()
                Snackbar.make(coordinatorLayoutChatRoom, throwable.toString(),
                        Snackbar.LENGTH_SHORT).show()
                setUpAdapter(presenter.parsingChat(throwable.toString(),
                        this@ChatRoomActivity))
            }
        })
    }

    private fun setUpAdapter(chatList: ArrayList<Chat>) {
        if (chatList.size != 0) {
            Picasso.with(this).load(chatList[0].avatar).into(imageViewChatRoomAvatar)
            textViewChatRoomTitle.text = chatList[0].sender
        }
        val adapter = ChatRoomAdapter(this, chatList, presenter)
        val layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, true)
        layoutManager.stackFromEnd = true
        recyclerViewChatRoom.layoutManager = layoutManager
        recyclerViewChatRoom.isNestedScrollingEnabled = false
        recyclerViewChatRoom.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun hideDialog() {
        if (loading!!.isShowing)
            loading!!.dismiss()
    }
}
