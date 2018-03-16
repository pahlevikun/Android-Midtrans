package com.midtrans.chatapp.model.database


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.res.Resources
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.midtrans.chatapp.model.pojo.Chat
import java.util.*

/**
 * Created by farhan on 3/16/18.
 */

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    var res: Resources = context.resources

    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "ChatApp"
        val TABLE_CACHE = "cache"
        val KEY_ID = "_id"
        val KEY_SENDER = "sender"
        val KEY_AVATAR = "avatar"
        val KEY_MESSAGE = "message"
        val KEY_SENT_AT = "sent_at"
    }


    // Creating Tables
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = ("CREATE TABLE IF NOT EXISTS " + TABLE_CACHE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_SENDER + " TEXT,"
                + KEY_AVATAR + " TEXT," + KEY_MESSAGE + " TEXT,"
                + KEY_SENT_AT + " TEXT" + ")")

        db.execSQL(CREATE_TABLE)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//        db.execSQL("DROP TABLE IF EXISTS $TABLE_CACHE")
        onCreate(db)
    }

    val getAllCache: ArrayList<Chat>
        @SuppressLint("Recycle")
        get() {
            val cacheList = ArrayList<Chat>()
            val selectQuery = "SELECT * FROM $TABLE_CACHE"

            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val cache = Chat(Integer.parseInt(cursor.getString(0)),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4))
                    cacheList.add(cache)
                } while (cursor.moveToNext())
            }
            return cacheList
        }

    fun deleteCache() {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_CACHE")
    }

    fun addCache(chat: Chat) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(KEY_ID, chat.id)
        values.put(KEY_SENDER, chat.sender)
        values.put(KEY_AVATAR, chat.avatar)
        values.put(KEY_MESSAGE, chat.message)
        values.put(KEY_SENT_AT, chat.sent_at)

        db.insert(TABLE_CACHE, null, values)
        db.close()
    }

}
