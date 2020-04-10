package com.logistics.logix.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "consignmentDetail")
class ConsignmentDetail (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var containerId: String,
    var deploymentDestination: String,
    var typesOfGoodsTransported: String,
    var deploymentDate: String,
    var freightForwarderAssociated: String,
    var createdBy: Int
)