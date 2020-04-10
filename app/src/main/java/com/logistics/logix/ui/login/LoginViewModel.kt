package com.logistics.logix.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.logistics.logix.database.LogixDb
import com.logistics.logix.database.model.User
import com.logistics.logix.database.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val userRepository: UserRepository

    init {
        val userDao = LogixDb.getDatabase(application).userDao()
        userRepository = UserRepository(userDao)
    }

    fun insertUser(user: User) = scope.launch(Dispatchers.IO) {
        userRepository.insert(user)
    }

    fun updateUser(user: User) = scope.launch(Dispatchers.IO) {
        userRepository.updateUser(user)
    }

    suspend fun getUser(email: String, password: String) : User {
        return userRepository.getUser(email, password)
    }

    suspend fun getUserById(id: Int) : User {
        return userRepository.getUserById(id)
    }

}