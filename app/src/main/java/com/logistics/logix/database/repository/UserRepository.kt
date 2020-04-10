package com.logistics.logix.database.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.Query
import com.logistics.logix.database.dao.UserDao
import com.logistics.logix.database.model.User

class UserRepository(private val userDao: UserDao) {

    suspend fun allUsers(): List<User> {
        return userDao.getUsers()
    }

    @WorkerThread
    fun insert(user: User) {
        userDao.insert(user)
    }

    suspend fun getUser(email: String, password: String): User{
        return userDao.getUser(email, password)
    }

    suspend fun getUserById(id: Int): User{
        return userDao.getUserById(id)
    }

    fun updateUser(user: User){
        userDao.updateUser(user)
    }

    fun deleteUserTable(){
        userDao.deleteUserTable()
    }

}