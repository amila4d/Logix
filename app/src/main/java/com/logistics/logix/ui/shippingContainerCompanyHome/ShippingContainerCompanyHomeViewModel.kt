package com.logistics.logix.ui.shippingContainerCompanyHome

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.logistics.logix.database.LogixDb
import com.logistics.logix.database.model.ConsignmentDetail
import com.logistics.logix.database.model.User
import com.logistics.logix.database.repository.ConsignmentDetailRepository
import com.logistics.logix.database.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ShippingContainerCompanyHomeViewModel (application: Application) : AndroidViewModel(application) {
    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val userRepository: UserRepository
    private val consignmentDetailRepository: ConsignmentDetailRepository

    init {
        val userDao = LogixDb.getDatabase(application).userDao()
        val consignmentDetailDap = LogixDb.getDatabase(application).consignmentDetailDap()
        userRepository = UserRepository(userDao)
        consignmentDetailRepository = ConsignmentDetailRepository(consignmentDetailDap)
    }

    fun insertUser(user: User) = scope.launch(Dispatchers.IO) {
        userRepository.insert(user)
    }

    suspend fun getUser(email: String, password: String) : User {
        return userRepository.getUser(email, password)
    }

    suspend fun getConsignmentDetail(createdUserId: Int) : List<ConsignmentDetail> {
        return consignmentDetailRepository.getConsignmentDetail(createdUserId)
    }

    suspend fun getAllUser(): List<User>{
        return userRepository.allUsers()
    }
}