package com.logistics.logix.ui.consignmentDetails

import android.app.Application
import android.view.Display
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.logistics.logix.database.LogixDb
import com.logistics.logix.database.model.ConsignmentDetail
import com.logistics.logix.database.model.Search
import com.logistics.logix.database.model.User
import com.logistics.logix.database.repository.ConsignmentDetailRepository
import com.logistics.logix.database.repository.SearchRepository
import com.logistics.logix.database.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import kotlin.coroutines.CoroutineContext

class ConsignmentDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val userRepository: UserRepository
    private val searchRepository: SearchRepository
    private val consignmentDetailRepository: ConsignmentDetailRepository

    init {
        val userDao = LogixDb.getDatabase(application).userDao()
        val searchDao = LogixDb.getDatabase(application).searchDao()
        val consignmentDetailDao = LogixDb.getDatabase(application).consignmentDetailDap()
        userRepository = UserRepository(userDao)
        searchRepository = SearchRepository(searchDao)
        consignmentDetailRepository = ConsignmentDetailRepository(consignmentDetailDao)
    }

    fun insertSearch(search: Search) = scope.launch  (Dispatchers.IO){
        searchRepository.insert(search)
    }

    fun insertConsignmentDetail(consignmentDetail: ConsignmentDetail) = scope.launch (Dispatchers.IO) {
        consignmentDetailRepository.insert(consignmentDetail)
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