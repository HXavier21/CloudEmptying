package com.example.cloudemptying.questionnaire

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.tencent.mmkv.MMKV

private const val TAG = "JsonProvider"

val kv = MMKV.defaultMMKV()

class JsonProvider:ContentProvider(){

    private val jsonIndex = 0
    private val authority = "com.example.wintercamp.questionnaire.provider"

    private val uriMatcher by lazy {
        val matcher = UriMatcher(UriMatcher.NO_MATCH)
        matcher.addURI(authority,"json",jsonIndex)
        matcher
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun getType(uri: Uri): String? =when(uriMatcher.match(uri)){
        jsonIndex -> "vnd.android.cursor.dir/vnd.com.example.wintercamp.questionnaire.provider.json"
        else -> null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when(uriMatcher.match(uri)){
            jsonIndex->{
                kv.encode("json",values?.getAsString("json")?: com.example.cloudemptying.network.Encode(obj))
                Log.d(TAG, kv.decodeString("json")?:"null")
            }
        }
        return null
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }


    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }

}