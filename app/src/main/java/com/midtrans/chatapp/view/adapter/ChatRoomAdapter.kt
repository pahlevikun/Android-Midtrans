package com.midtrans.chatapp.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leocardz.link.preview.library.LinkPreviewCallback
import com.leocardz.link.preview.library.SourceContent
import com.leocardz.link.preview.library.TextCrawler
import com.midtrans.chatapp.R
import com.midtrans.chatapp.model.pojo.Chat
import com.midtrans.chatapp.presenter.impls.ChatRoomPresenter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_chat_self.view.*
import kotlinx.android.synthetic.main.adapter_chat_user.view.*
import java.util.*


/**
 * Created by farhan on 3/16/18.
 */

class ChatRoomAdapter(private val context: Context, private val listChat: ArrayList<Chat>,
                      private val presenter: ChatRoomPresenter)
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
        viewHolder.bindType(listChat[i], context,presenter)
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

        open fun bindType(item: Chat, context: Context, presenter: ChatRoomPresenter) {

        }

    }

    class ViewHolderUser(itemView: View) : ItemRowHolder(itemView) {

        init {
            itemView.textChatBodyUser
            itemView.textChatTimeUser
            itemView.textChatPreviewUser
            itemView.linearLayoutChatPreviewUser
            itemView.imageViewChatPreviewUser
        }

        override fun bindType(item: Chat, context: Context, presenter: ChatRoomPresenter) {
            itemView.textChatTimeUser.text = presenter.convertTime(item.sent_at)
            itemView.textChatBodyUser.text = item.message
            if (item.message.startsWith("http")) {
                if (presenter.isOnline()){
                    val textCrawler = TextCrawler()
                    val linkPreviewCallback = object : LinkPreviewCallback {
                        override fun onPre() {
                        }

                        @SuppressLint("SetTextI18n")
                        override fun onPos(sourceContent: SourceContent, b: Boolean) {
                            itemView.linearLayoutChatPreviewUser.visibility = View.VISIBLE
                            itemView.textChatPreviewUser.text = sourceContent.description
                            Picasso
                                    .with(context)
                                    .load(sourceContent.images.toString()
                                            .replace("[", "")
                                            .replace("]", ""))
                                    .into(itemView.imageViewChatPreviewUser)
                            itemView.linearLayoutChatPreviewUser.setOnClickListener {
                                val intent = Intent(Intent.ACTION_VIEW)
                                intent.data = Uri.parse(item.message)
                                context.startActivity(intent)
                            }
                        }
                    }
                    textCrawler.makePreview(linkPreviewCallback, item.message)
                }
            }
        }

    }

    class ViewHolderSelf(itemView: View) : ItemRowHolder(itemView) {

        init {
            itemView.textChatBodySelf
            itemView.textChatTimeSelf
            itemView.textChatPreviewSelf
            itemView.imageViewChatPreviewSelf
            itemView.linearLayoutChatPreviewSelf
        }

        override fun bindType(item: Chat, context: Context,presenter: ChatRoomPresenter) {
            itemView.textChatTimeSelf.text = presenter.convertTime(item.sent_at)
            itemView.textChatBodySelf.text = item.message
            if (item.message.startsWith("https")) {
                if (presenter.isOnline()){
                    val textCrawler = TextCrawler()
                    val linkPreviewCallback = object : LinkPreviewCallback {
                        override fun onPre() {
                            itemView.textChatBodySelf.text = item.message
                        }

                        @SuppressLint("SetTextI18n")
                        override fun onPos(sourceContent: SourceContent, b: Boolean) {
                            itemView.linearLayoutChatPreviewSelf.visibility = View.VISIBLE
                            itemView.textChatPreviewSelf.text = sourceContent.description
                            Picasso
                                    .with(context)
                                    .load(sourceContent.images.toString()
                                            .replace("[", "")
                                            .replace("]", ""))
                                    .into(itemView.imageViewChatPreviewSelf)
                            itemView.linearLayoutChatPreviewSelf.setOnClickListener {
                                val intent = Intent(Intent.ACTION_VIEW)
                                intent.data = Uri.parse(item.message)
                                context.startActivity(intent)
                            }
                        }
                    }
                    textCrawler.makePreview(linkPreviewCallback, item.message)
                }
            }
        }
    }


}

