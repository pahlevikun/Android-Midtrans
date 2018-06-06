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
import com.midtrans.chatapp.model.Datum
import com.midtrans.chatapp.model.pojo.Chat
import com.midtrans.chatapp.presenter.impls.ChatRoomPresenter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_chat_self.view.*
import kotlinx.android.synthetic.main.adapter_chat_user.view.*
import java.util.*


/**
 * Created by farhan on 3/16/18.
 */

class ChatRoomAdapter(private val context: Context, private val listChat: List<Datum>,
                      private val presenter: ChatRoomPresenter)
    : RecyclerView.Adapter<ChatRoomAdapter.ItemRowHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ChatRoomAdapter.ItemRowHolder {
        //Set layout based on getItemViewType, if isSelf return 1 else 0
        return when (i) {
            1 -> {
                //This is layout for right box (self)
                val view = LayoutInflater
                        .from(viewGroup.context)
                        .inflate(R.layout.adapter_chat_self, viewGroup, false)
                ViewHolderSelf(view)
            }
            else -> {
                //This is layout for left box (user)
                val view = LayoutInflater
                        .from(viewGroup.context)
                        .inflate(R.layout.adapter_chat_user, viewGroup, false)
                ViewHolderUser(view)
            }
        }

    }

    //Casting Interger into boolean
    private fun Int.toBoolean() = this % 2 == 0

    override fun onBindViewHolder(viewHolder: ChatRoomAdapter.ItemRowHolder, i: Int) {
        //Because of 2 type of layout, make new class for handling 2 type of layout
        viewHolder.bindType(listChat[i], context, presenter)
    }

    //This is some implementation of adapter interface for checking type
    override fun getItemViewType(position: Int): Int = if (position.toBoolean()) {
        0
    } else {
        1
    }

    override fun getItemCount(): Int {
        return listChat.size
    }

    open class ItemRowHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        open fun bindType(item: Datum, context: Context, presenter: ChatRoomPresenter) {

        }

    }

    //User viewholder
    class ViewHolderUser(itemView: View) : ItemRowHolder(itemView) {

        //Declaring view
        init {
            itemView.textChatBodyUser
            itemView.textChatTimeUser
            itemView.textChatPreviewUser
            itemView.linearLayoutChatPreviewUser
            itemView.imageViewChatPreviewUser
        }

        //Override bindType so the row now automatically use its layout
        override fun bindType(item: Datum, context: Context, presenter: ChatRoomPresenter) {
            itemView.textChatTimeUser.text = presenter.convertTime(item.sentAt!!)
            itemView.textChatBodyUser.text = item.message
            //This is for make link preview based on chat with only contains link (start with http)
            if (item.message!!.startsWith("http")) {
                if (presenter.isOnline()) {
                    val textCrawler = TextCrawler()
                    val linkPreviewCallback = object : LinkPreviewCallback {
                        override fun onPre() {
                        }

                        @SuppressLint("SetTextI18n")
                        override fun onPos(sourceContent: SourceContent, b: Boolean) {
                            //after getting result in onPos, show the previewLayout
                            itemView.linearLayoutChatPreviewUser.visibility = View.VISIBLE
                            itemView.textChatPreviewUser.text = sourceContent.description
                            Picasso
                                    .with(context)
                                    .load(sourceContent.images.toString()
                                            .replace("[", "")
                                            .replace("]", ""))
                                    .into(itemView.imageViewChatPreviewUser)
                            //if previewLayout clicked, it intent to browser for open link
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

    //Same description as before but it for right box (self)
    class ViewHolderSelf(itemView: View) : ItemRowHolder(itemView) {

        init {
            itemView.textChatBodySelf
            itemView.textChatTimeSelf
            itemView.textChatPreviewSelf
            itemView.imageViewChatPreviewSelf
            itemView.linearLayoutChatPreviewSelf
        }

        override fun bindType(item: Datum, context: Context, presenter: ChatRoomPresenter) {
            itemView.textChatTimeSelf.text = presenter.convertTime(item.sentAt!!)
            itemView.textChatBodySelf.text = item.message
            if (item.message!!.startsWith("https")) {
                if (presenter.isOnline()) {
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

