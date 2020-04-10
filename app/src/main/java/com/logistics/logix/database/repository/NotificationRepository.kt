package com.logistics.logix.database.repository

import androidx.annotation.WorkerThread
import com.logistics.logix.database.dao.NotificationDao
import com.logistics.logix.database.model.Notification

class NotificationRepository(private val notificationDao: NotificationDao){


    @WorkerThread
    fun insert(notification: Notification) {
        notificationDao.insertNotification(notification)
    }

    fun deleteNotificationTable(){
        notificationDao.deleteNotificationTable()
    }

}