package com.logistics.logix.database.repository

import androidx.annotation.WorkerThread
import com.logistics.logix.database.dao.NotificationDao
import com.logistics.logix.database.model.Notification
import java.util.*

class NotificationRepository(private val notificationDao: NotificationDao){

    /*suspend fun allNotifications(from: Date, to: Date): List<Notification> {
        return notificationDao.getNotifications(from, to)
    }*/

    @WorkerThread
    fun insert(notification: Notification) {
        notificationDao.insertNotification(notification)
    }

    fun deleteNotificationTable(){
        notificationDao.deleteNotificationTable()
    }

}