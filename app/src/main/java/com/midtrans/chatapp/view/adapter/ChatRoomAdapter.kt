package com.midtrans.chatapp.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.midtrans.chatapp.R
import com.midtrans.chatapp.model.pojo.Chat
import kotlinx.android.synthetic.main.adapter_chat_self.view.*
import kotlinx.android.synthetic.main.adapter_chat_user.view.*
import java.util.*

/**
 * Created by farhan on 3/16/18.
 */

class ChatRoomAdapter(private val context: Context, private val listChat: ArrayList<Chat>)
    : RecyclerView.Adapter<ChatRoomAdapter.ItemRowHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ChatRoomAdapter.ItemRowHolder {
        return when (i) {
            1 -> {
                val view = LayoutInflater
                        .from(viewGroup.context)
                        .inflate(R.layout.adapter_chat_self, viewGroup, false)
                ViewHolderSelf(view)
            }
            else -> {
                val view = LayoutInflater
                        .from(viewGroup.context)
                        .inflate(R.layout.adapter_chat_user, viewGroup, false)
                ViewHolderUser(view)
            }
        }

    }

    override fun onBindViewHolder(viewHolder: ChatRoomAdapter.ItemRowHolder, i: Int) {
        viewHolder.bindType(listChat[i], context)
    }


    override fun getItemViewType(position: Int): Int = if (listChat[position].isSelf) {
        0
    } else {
        1
    }

    override fun getItemCount(): Int {
        return listChat.size
    }

    open class ItemRowHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        open fun bindType(item: Chat, context: Context) {

        }

    }

    class ViewHolderUser(itemView: View) : ItemRowHolder(itemView) {


        init {
            itemView.textChatBodyUser
            itemView.textChatTimeUser
        }

        override fun bindType(item: Chat, context: Context) {
            itemView.textChatBodyUser.text = item.message
            itemView.textChatTimeUser.text = item.sent_at
        }

    }

    class ViewHolderSelf(itemView: View) : ItemRowHolder(itemView) {


        init {
            itemView.textChatBodySelf
            itemView.textChatTimeSelf
        }

        override fun bindType(item: Chat, context: Context) {
            itemView.textChatBodySelf.text = item.message
            itemView.textChatTimeSelf.text = item.sent_at
        }

    }
}

