package com.logistics.logix.database.repository

import androidx.annotation.WorkerThread
import com.logistics.logix.database.dao.SearchDao
import com.logistics.logix.database.model.Search

class SearchRepository(private val searchDao: SearchDao) {

    suspend fun allSearches(): List<Search> {
        return searchDao.getSearches()
    }

    @WorkerThread
    fun insert(search: Search) {
        searchDao.insertSearch(search)
    }

    suspend fun getSearch(companyName: String, containerSize: Int): Search{
        return searchDao.getSearch(companyName, containerSize)
    }

    fun deleteSearchTable(){
        //searchDao.deleteUserTable()
    }

}