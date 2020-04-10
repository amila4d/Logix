package com.logistics.logix.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.logistics.logix.database.dao.ConsignmentDetailDao
import com.logistics.logix.database.dao.SearchDao
import com.logistics.logix.database.dao.UserDao
import com.logistics.logix.database.model.ConsignmentDetail
import com.logistics.logix.database.model.Search
import com.logistics.logix.database.model.User


@Database(
        entities = [
            User::class,
            Search::class,
            ConsignmentDetail::class],
        version = 1)
@TypeConverters(Converters::class)
abstract class LogixDb : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun searchDao(): SearchDao
    abstract fun consignmentDetailDap(): ConsignmentDetailDao

    companion object {

        @Volatile
        private var INSTANCE: LogixDb? = null

        fun getDatabase(context: Context): LogixDb {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        LogixDb::class.java,
                        "logix_db"
                )
                        .fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
