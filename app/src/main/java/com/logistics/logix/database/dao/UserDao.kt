package com.logistics.logix.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.logistics.logix.database.model.User


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("SELECT * FROM user where email= :email and password= :password")
    suspend fun getUser(email: String, password: String): User

    @Query("SELECT * FROM user where id= :id")
    suspend fun getUserById(id: Int): User

    @Query("SELECT * FROM user")
    fun getAllUser(): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE email = (SELECT MAX(email)  FROM user)")
    fun getCurrentUser(): LiveData<User>

    @Query("DELETE FROM user WHERE email = :email")
    fun deleteUser(email: String)

    @Query("DELETE FROM user")
    fun deleteUserTable()

    @Update
    fun updateUser(user: User)

    @Update
    fun updateUsers(vararg users: User)

    @Query("DELETE FROM user")
    fun deleteAllUsers()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("SELECT * FROM user")
    suspend fun getUsers(): List<User>

//    @Query("SELECT * FROM user WHERE login = :login")
//    fun findByLogin(login: String): LiveData<MobileUser>

}
