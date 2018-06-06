package com.midtrans.chatapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ChatGson {
    @SerializedName("status")
    @Expose
    var status: Int? = null
    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null
}
