package com.logistics.logix.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.logistics.logix.database.model.Notification

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotification(notification: Notification)

    @Query("SELECT * FROM notification WHERE createdBy = :createdUserId")
    suspend fun getAllNotificationByCreatedUser(createdUserId: Int): List<Notification>

    @Query("DELETE FROM notification")
    fun deleteNotificationTable()

}