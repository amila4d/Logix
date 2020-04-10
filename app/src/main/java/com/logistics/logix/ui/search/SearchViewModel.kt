package com.logistics.logix.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.logistics.logix.database.LogixDb
import com.logistics.logix.database.model.Search
import com.logistics.logix.database.model.User
import com.logistics.logix.database.repository.SearchRepository
import com.logistics.logix.database.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val userRepository: UserRepository
    private val searchRepository: SearchRepository

    init {
        val userDao = LogixDb.getDatabase(application).userDao()
        val searchDao = LogixDb.getDatabase(application).searchDao()
        userRepository = UserRepository(userDao)
        searchRepository = SearchRepository(searchDao)
    }

    fun insertSearch(search: Search) = scope.launch  (Dispatchers.IO){
        searchRepository.insert(search)
    }

    fun insertUser(user: User) = scope.launch(Dispatchers.IO) {
        userRepository.insert(user)
    }

    suspend fun getUser(email: String, password: String) : User {
        return userRepository.getUser(email, password)
    }

    suspend fun getAllUser(): List<User>{
        return userRepository.allUsers()
    }

    suspend fun getAllSearches(): List<Search>{
        return searchRepository.allSearches()
    }
}