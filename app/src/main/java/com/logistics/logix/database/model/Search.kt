package com.logistics.logix.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search")
data class Search (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var preferredCompanyName: String,
    var sizeOfContainer: Int,
    var typesOfGoods: String,
    var dateRequired: String,
    var requestedUserId: Int,
    var createdBy: Int
)