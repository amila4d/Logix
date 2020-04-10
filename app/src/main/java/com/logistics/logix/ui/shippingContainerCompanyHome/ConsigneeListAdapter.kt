package com.logistics.logix.ui.shippingContainerCompanyHome

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.logistics.logix.R
import com.logistics.logix.database.model.ConsignmentDetail
import kotlinx.android.synthetic.main.list_item_consignee.view.*

class ConsigneeListAdapter (var consignmentDetails: MutableList<ConsignmentDetail>) :
    RecyclerView.Adapter<ConsigneeListAdapter.UserViewHolder>() {
    fun updateUsers(details: List<ConsignmentDetail>) {
        consignmentDetails.clear()
        consignmentDetails.addAll(details)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = UserViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_consignee, parent, false)
    )
    override fun getItemCount() = consignmentDetails.size
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(consignmentDetails[position])
    }
    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvConsineeName = view.tvConsineeName
        private val tvDestination = view.tvDestination

        fun bind(consignmentDetail: ConsignmentDetail) {
            tvConsineeName.text = consignmentDetail.containerId
            tvDestination.text = consignmentDetail.deploymentDestination
        }
    }
}