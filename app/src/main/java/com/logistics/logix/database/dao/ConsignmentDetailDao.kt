package com.logistics.logix.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.logistics.logix.database.model.ConsignmentDetail
import com.logistics.logix.database.model.User


@Dao
interface ConsignmentDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(consignmentDetail: ConsignmentDetail)

    @Query("SELECT * FROM ConsignmentDetail where containerId= :containerId ")
    suspend fun getUser(containerId: String): ConsignmentDetail

    @Query("SELECT * FROM ConsignmentDetail WHERE createdBy = :createdUserId")
    suspend fun getAllConsignmentDetailByCreatedUser(createdUserId: Int): List<ConsignmentDetail>


    @Query("DELETE FROM ConsignmentDetail")
    fun deleteConsignmentDetailTable()

    @Update
    fun updateUser(user: User)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(consignmentDetail: ConsignmentDetail)


}
