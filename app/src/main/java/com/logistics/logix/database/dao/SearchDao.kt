package com.logistics.logix.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.logistics.logix.database.model.Search

@Dao
interface SearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearch(search: Search)

    @Query("SELECT * FROM search where preferredCompanyName = :preferredCompanyName and sizeOfContainer = :sizeOfContainer")
    suspend fun getSearch(preferredCompanyName: String, sizeOfContainer: Int): Search

    @Query("SELECT * FROM search")
    fun getAllUser(): LiveData<List<Search>>

    @Query("SELECT * FROM search WHERE createdBy = :createdUserId")
    fun getAllUserByCreatedUser(createdUserId: Int): LiveData<List<Search>>

    @Query("SELECT * FROM search")
    suspend fun getSearches(): List<Search>

}