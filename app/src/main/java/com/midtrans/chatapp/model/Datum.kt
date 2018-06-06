package com.midtrans.chatapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Datum {

    @SerializedName("sender")
    @Expose
    var sender: String? = null
    @SerializedName("avatar")
    @Expose
    var avatar: String? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("sent_at")
    @Expose
    var sentAt: String? = null

}
