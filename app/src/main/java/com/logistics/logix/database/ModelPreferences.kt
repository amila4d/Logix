package com.logistics.logix.database

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder

class ModelPreferences (context: Context) {

    private val preferences = context.getSharedPreferences("MODEL_PREFERENCES", Context.MODE_PRIVATE)
    private val editor = preferences.edit()
    private val gson = GsonBuilder().create()

    fun <T> putObject(key: String, y: T) {
        val inString = gson.toJson(y)
        editor.putString(key, inString).commit()
    }

    fun <T> getObject(key: String, c: Class<T>): T? {
        val value = preferences.getString(key, null)
        if (value != null) {
            return gson.fromJson(value, c)
        } else {
            Log.i("Logix", "No object with key: $key was saved")
            return null
        }
    }
}