package com.logistics.logix.database.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.Query
import com.logistics.logix.database.dao.ConsignmentDetailDao
import com.logistics.logix.database.dao.SearchDao
import com.logistics.logix.database.dao.UserDao
import com.logistics.logix.database.model.ConsignmentDetail
import com.logistics.logix.database.model.Search
import com.logistics.logix.database.model.User

class ConsignmentDetailRepository(private val consignmentDetailDao: ConsignmentDetailDao) {


    @WorkerThread
    fun insert(consignmentDetail: ConsignmentDetail) {
        consignmentDetailDao.insert(consignmentDetail)
    }

    fun deleteConsignmentDetailTable(){
        consignmentDetailDao.deleteConsignmentDetailTable()
    }

    suspend fun getConsignmentDetail(createdUserId: Int): List<ConsignmentDetail>{
        return consignmentDetailDao.getAllConsignmentDetailByCreatedUser(createdUserId)
    }
}