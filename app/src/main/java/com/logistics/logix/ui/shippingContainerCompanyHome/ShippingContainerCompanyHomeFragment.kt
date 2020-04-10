package com.logistics.logix.ui.shippingContainerCompanyHome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.logistics.logix.R
import com.logistics.logix.database.ModelPreferences
import com.logistics.logix.database.model.User
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.get

class ShippingContainerCompanyHomeFragment : Fragment() {

    private val modelPreferences: ModelPreferences = get()
    private lateinit var user: User
    private var mAdapter: ConsigneeListAdapter? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var shippingContainerCompanyHomeViewModel: ShippingContainerCompanyHomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        shippingContainerCompanyHomeViewModel =
            ViewModelProviders.of(this).get(ShippingContainerCompanyHomeViewModel::class.java)
        user = modelPreferences.getObject("user", User::class.java)!!
        val root = inflater.inflate(R.layout.fragment_shipping_container_company_home, container, false)
        recyclerView = root.findViewById(R.id.consigneeListView)
        loadConsignees()
        return root
    }

    private fun loadConsignees(){
        runBlocking {
            val consigneeList = shippingContainerCompanyHomeViewModel.getConsignmentDetail(user!!.id)
            if(consigneeList.isNotEmpty()) {
                mAdapter = ConsigneeListAdapter(consigneeList.toMutableList())
                val mLayoutManager = LinearLayoutManager(context)
                recyclerView.layoutManager = mLayoutManager
                recyclerView.itemAnimator = DefaultItemAnimator()
                recyclerView.invalidateItemDecorations()
                recyclerView.adapter = mAdapter
            }else{
                Toast.makeText(context,"No data available to show",Toast.LENGTH_SHORT).show()
            }
        }
    }

}