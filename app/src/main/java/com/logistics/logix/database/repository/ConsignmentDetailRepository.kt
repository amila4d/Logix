package com.logistics.logix.database.repository

import androidx.annotation.WorkerThread
import com.logistics.logix.database.dao.ConsignmentDetailDao
import com.logistics.logix.database.model.ConsignmentDetail

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