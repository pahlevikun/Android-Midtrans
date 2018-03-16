package com.midtrans.chatapp.etc.retrofit

import com.midtrans.chatapp.etc.config.APIConfig

/**
 * Created by farhan on 3/16/18.
 */

object UtilsApi {
    // Mendeklarasikan Interface BaseApiService
    val apiService: BaseApiService
        get() = RetrofitClient.getClient(APIConfig.END_POINT).create(BaseApiService::class.java)
}
