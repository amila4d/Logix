package com.logistics.logix.service.api

import com.logistics.logix.database.model.Search
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {

    @GET("searchResult.json")
    fun getSearchResults() : Call<List<Search>>

    companion object {

        var BASE_URL = "http://logix.com/apis/"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }

}