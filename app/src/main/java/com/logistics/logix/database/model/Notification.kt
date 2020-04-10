package com.logistics.logix.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notification")
data class Notification (
        @PrimaryKey(autoGenerate = true)
        var id: Int,
        var companyName: String,
        var title: String,
        var type: String,
        var content: String,
        var createdBy: Int
)