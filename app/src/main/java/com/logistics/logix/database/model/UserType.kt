package com.logistics.logix.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userType")
data class UserType  (
    @PrimaryKey
    val id: Int,
    val userType: String
)