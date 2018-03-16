package com.midtrans.chatapp.etc.retrofit

import com.midtrans.chatapp.etc.config.APIConfig
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by farhan on 3/16/18.
 */

interface BaseApiService {

    @GET(APIConfig.GET_CHAT)
    fun getChat(): Call<ResponseBody>

}
