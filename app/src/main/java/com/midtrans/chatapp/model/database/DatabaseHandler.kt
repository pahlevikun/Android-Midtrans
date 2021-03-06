package com.midtrans.chatapp.model.database


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.res.Resources
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.midtrans.chatapp.model.pojo.Chat
import java.util.*

/**
 * Created by farhan on 3/16/18.
 */

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    var res: Resources = context.resources

    companion object {
        val DATABASE_VERSION = 2
        val DATABASE_NAME = "ChatApp"
        val TABLE_CACHE = "cache"
        val KEY_ID = "_id"
        val KEY_SENDER = "sender"
        val KEY_AVATAR = "avatar"
        val KEY_MESSAGE = "message"
        val KEY_SENT_AT = "sent_at"
        val KEY_SELF = "self"
    }


    //Creating Table
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = ("CREATE TABLE IF NOT EXISTS " + TABLE_CACHE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_SENDER + " TEXT,"
                + KEY_AVATAR + " TEXT," + KEY_MESSAGE + " TEXT,"
                + KEY_SENT_AT + " TEXT," + KEY_SELF + " INTEGER" + ")")

        db.execSQL(CREATE_TABLE)
    }


    //Action for upgrade the database
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//        db.execSQL("DROP TABLE IF EXISTS $TABLE_CACHE")
        onCreate(db)
    }

    //getting arraylist
    val getAllCache: ArrayList<Chat>
        @SuppressLint("Recycle")
        get() {
            //Declare variable and query
            val cacheList = ArrayList<Chat>()
            val selectQuery = "SELECT * FROM $TABLE_CACHE"

            //Open database
            val db = this.writableDatabase
            //Run query
            val cursor = db.rawQuery(selectQuery, null)
            //Inserting data to list with cursor
            if (cursor.moveToFirst()) {
                do {
                    val cache = Chat(Integer.parseInt(cursor.getString(0)),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getInt(5).toBoolean())
                    cacheList.add(cache)
                } while (cursor.moveToNext())
            }
            db.close()
            return cacheList
        }

    //Casting
    private fun Int.toBoolean() = this == 1
    private fun Boolean.toInteger() = if (this) 1 else 0

    //For delete cache
    fun deleteCache() {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_CACHE")
        db.close()
    }

    //For inserting data
    fun addCache(chat: Chat) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(KEY_ID, chat.id)
        values.put(KEY_SENDER, chat.sender)
        values.put(KEY_AVATAR, chat.avatar)
        values.put(KEY_MESSAGE, chat.message)
        values.put(KEY_SENT_AT, chat.sent_at)
        values.put(KEY_SELF, chat.isSelf.toInteger())

        db.insert(TABLE_CACHE, null, values)
        db.close()
    }

}
