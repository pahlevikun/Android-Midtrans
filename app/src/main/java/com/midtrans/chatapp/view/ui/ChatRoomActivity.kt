@file:Suppress("DEPRECATION")

package com.midtrans.chatapp.view.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.midtrans.chatapp.R
import com.midtrans.chatapp.model.ChatGson
import com.midtrans.chatapp.model.Datum
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

    //Declaring presenter and progressdialog
    private val presenter = ChatRoomPresenter()
    private var loading: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        //Set progress dialog and show it
        loading = ProgressDialog.show(this, getString(R.string.progress_loading),
                getString(R.string.progress_getting), false, false)
        //Call getchat from presenter
        presenter.getChat(object : ServerCallback {
            override fun onSuccess(response: String) {
                //hide progress dialog
                hideDialog()
                //parse data from presenter and set it to recyclerview
                setUpAdapter(presenter.parsingChat(response, this@ChatRoomActivity))
            }

            override fun onFailed(response: String) {
                //hide progress dialog
                hideDialog()
                //Show snackbar because of failed
                Snackbar.make(coordinatorLayoutChatRoom, response, Snackbar.LENGTH_SHORT).show()
                //parse data from presenter and set it to recyclerview
                setUpAdapter(presenter.parsingChat(response, this@ChatRoomActivity))
            }

            override fun onFailure(throwable: Throwable) {
                //hide progress dialog
                hideDialog()
                //Show snackbar because of failed
                Snackbar.make(coordinatorLayoutChatRoom, throwable.toString(),
                        Snackbar.LENGTH_SHORT).show()
                //parse data from presenter and set it to recyclerview
                setUpAdapter(presenter.parsingChat(throwable.toString(),
                        this@ChatRoomActivity))
            }
        })
    }

    private fun setUpAdapter(data: ChatGson) {
        //if chatlist isn't empty, set the avatar and username
        val chatList = data.data
        if (chatList!=null || chatList!!.isNotEmpty()) {
                Picasso.with(this).load(chatList[0].avatar).into(imageViewChatRoomAvatar)
                textViewChatRoomTitle.text = chatList[0].sender
        }
        //Declaring adapter and layout manager
        val adapter = ChatRoomAdapter(this, chatList, presenter)
        val layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, true)
        //Set layout manager from top to bottom (normally bottom to top because of reverLayout)
        layoutManager.stackFromEnd = true
        //Set the layout manager to recyclerView
        recyclerViewChatRoom.layoutManager = layoutManager
        //Disable nested scrolling
        recyclerViewChatRoom.isNestedScrollingEnabled = false
        //Set the adapter to recyclerview
        recyclerViewChatRoom.adapter = adapter
        //Refresh adapter with notify
        adapter.notifyDataSetChanged()
    }

    private fun hideDialog() {
        if (loading!!.isShowing)
            loading!!.dismiss()
    }
}
