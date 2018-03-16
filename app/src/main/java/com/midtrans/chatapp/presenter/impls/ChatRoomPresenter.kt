package com.midtrans.chatapp.presenter.impls

import android.util.Log
import com.midtrans.chatapp.etc.config.APIConfig
import com.midtrans.chatapp.etc.retrofit.BaseApiService
import com.midtrans.chatapp.etc.retrofit.UtilsApi
import com.midtrans.chatapp.model.pojo.Chat
import com.midtrans.chatapp.presenter.interfaces.ChatRoomInterface
import com.midtrans.chatapp.presenter.interfaces.ServerCallback
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback

/**
 * Created by farhan on 3/16/18.
 */
class ChatRoomPresenter : ChatRoomInterface {
    override fun getChat(callback: ServerCallback) {
        val mApiService: BaseApiService = UtilsApi.apiService
        mApiService.getChat()
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>,
                                            response: retrofit2.Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            callback.onSuccess(response.body()!!.string())
                        } else {
                            callback.onFailed(response.errorBody()!!.string())
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        callback.onFailure(t)
                    }
                })
    }

    override fun parsingChat(response: String): ArrayList<Chat> {
        val listChat: ArrayList<Chat> = ArrayList()
        try {
            val json = JSONObject(insertString(response,
                    "\"item\" : ",
                    response.indexOf("\"data\":{") + 11))
            val jsonData = json.getJSONObject("data")
            val jsonArrayData = jsonData.getJSONArray("item")
            (0 until jsonArrayData.length()).forEach { dataIndex ->
                val item = jsonArrayData.getJSONObject(dataIndex)
                val sender = item.getString("sender")
                val avatar = item.getString("avatar")
                val message = item.getString("message")
                val sentAt = item.getString("sent_at")
                listChat.add(Chat(dataIndex, sender, avatar, message, sentAt, dataIndex.toBoolean()))
            }
        } catch (e: Exception) {
            Log.e("${APIConfig.TAG} PARSING", e.toString())
        }
        return listChat
    }

    // put the marble in the bag
    private fun insertString(fullString: String, addString: String, index: Int): String {
        val stringBegin = fullString.substring(0, index)
        val stringEnd = fullString.substring(index)
        return stringBegin + addString + stringEnd
    }

    private fun Int.toBoolean() = this % 2 == 0
}