package com.midtrans.chatapp.presenter.impls

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import com.google.gson.GsonBuilder
import com.midtrans.chatapp.etc.config.APIConfig
import com.midtrans.chatapp.etc.retrofit.BaseApiService
import com.midtrans.chatapp.etc.retrofit.UtilsApi
import com.midtrans.chatapp.model.ChatGson
import com.midtrans.chatapp.model.database.DatabaseHandler
import com.midtrans.chatapp.model.pojo.Chat
import com.midtrans.chatapp.presenter.interfaces.ChatRoomInterface
import com.midtrans.chatapp.presenter.interfaces.ServerCallback
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by farhan on 3/16/18.
 */
class ChatRoomPresenter : ChatRoomInterface {

    //This is for convert sent_at to date format
    @SuppressLint("SimpleDateFormat")
    override fun convertTime(oldTime: String): String {
        //First you have declare SimpleDateFormat
        //This is for first parsing from string to date
        val oldDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        //After that we will convert it to time without date
        val newDateFormat = SimpleDateFormat("HH:mm")
        //Declaring variable
        val parsedDate: Date
        //Trycatch
        return try {
            //Because of unknown (in my opinion) sent_at format, we must
            //to delete 'T' String and delete string after '+'
            //After that, parse it to date with oldDateFormat
            parsedDate = oldDateFormat.parse(oldTime
                    .replace("T"," ")
                    .substring(0,oldTime.indexOf("+")))
            //For getting only time, format again with newDateFormat
            newDateFormat.format(parsedDate).toString()
        } catch (e: ParseException) {
            Log.e("$APIConfig CONVERT",e.toString())
            ""
        }
    }

    //This is for checking internet connection based on latency so use ping command
    override fun isOnline(): Boolean {
        try {
            val p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com")
            val returnVal = p1.waitFor()
            return (returnVal == 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    //This is method for getting chat, I use retrofit and passing it's response to callback
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

    //This is method for parsing json into arraylist with chat model
    //Because of invalid json format, I must to 're-engineer' the json
    //If failed parsing chat, we use data from local storage as a cache
    override fun parsingChat(response: String, context: Activity): ChatGson {
        //First declaring all variable
        var dataChat: ChatGson? = null
        try {
            val jsonGson = JSONObject(response)
            val gson = GsonBuilder().create()
            dataChat = gson.fromJson(jsonGson.toString(),ChatGson::class.java)
        } catch (e: Exception) {
            //If there's any exception like no json or other, set the listChat data from local db
            Log.e("${APIConfig.TAG} PARSING", e.toString())
        }
        return dataChat!!
    }

    //Insert string method for manipulating json
    private fun insertString(fullString: String, addString: String, index: Int): String {
        val stringBegin = fullString.substring(0, index)
        val stringEnd = fullString.substring(index)
        return stringBegin + addString + stringEnd
    }

    //Casting Interger into boolean
    private fun Int.toBoolean() = this % 2 == 0
}