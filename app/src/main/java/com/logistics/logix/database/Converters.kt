package com.logistics.logix.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.logistics.logix.database.model.Search
import com.logistics.logix.database.model.User

class Converters {

    @TypeConverter
    fun fromUser(user: User?): String? {
        if (user == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<User>() {

        }.type
        return gson.toJson(user, type)
    }

    @TypeConverter
    fun toUser(userString: String?): User? {
        if (userString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<User>() {

        }.type
        return gson.fromJson(userString, type)
    }

    @TypeConverter
    fun fromSearch(search: Search?): String? {
        if (search == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<Search>() {

        }.type
        return gson.toJson(search, type)
    }

    @TypeConverter
    fun toSearch(searchString: String?): Search? {
        if (searchString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<Search>() {

        }.type
        return gson.fromJson(searchString, type)
    }

}