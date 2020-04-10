package com.logistics.logix.ui.importerExporterHome

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.logistics.logix.R
import com.logistics.logix.database.model.Search
import kotlinx.android.synthetic.main.list_item_consignee.view.*

class CompanyListAdapter (var consignmentDetails: MutableList<Search>, val findNavController: NavController) :
    RecyclerView.Adapter<CompanyListAdapter.UserViewHolder>() {
    fun updateUsers(details: List<Search>) {
        consignmentDetails.clear()
        consignmentDetails.addAll(details)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = UserViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_consignee, parent, false), findNavController
    )
    override fun getItemCount() = consignmentDetails.size
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(consignmentDetails[position])
    }
    class UserViewHolder(view: View,val findNavController: NavController) : RecyclerView.ViewHolder(view) {
        val view = view
        private val tvConsineeName = view.tvConsineeName
        private val tvDestination = view.tvDestination

        fun bind(search: Search) {
            tvConsineeName.text = search.preferredCompanyName
            tvDestination.text = search.dateRequired
            view.setOnClickListener(View.OnClickListener {
                findNavController.popBackStack(R.id.nav_home_importer_exporter_home, true);
                findNavController.navigate(R.id.nav_chat)
            })
        }
    }
}