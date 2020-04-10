package com.logistics.logix.database.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user", indices = [Index(value = ["email"], unique = true)])
data class User(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        var email: String,
        var fullName: String,
        var password: String,
        var userType: String,
        var userTypeId: Int,
        var active: Int
)