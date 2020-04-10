package com.logistics.logix.ui.importerExporterHome

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
import kotlinx.coroutines.runBlocking

class ImporterExporterHomeFragment  : Fragment() {

    private lateinit var importerExporterHomeViewModel: ImporterExporterHomeViewModel
    private lateinit var recyclerView: RecyclerView
    private var mAdapter: CompanyListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        importerExporterHomeViewModel =
            ViewModelProviders.of(this).get(ImporterExporterHomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_importer_exporter_home, container, false)
        recyclerView = root.findViewById(R.id.companiesListView)
        loadCompanies()
        return root
    }

    private fun loadCompanies(){
        runBlocking {
            val searchList = importerExporterHomeViewModel.getAllSearches()
            if(searchList.isNotEmpty()) {
                mAdapter = CompanyListAdapter(searchList.toMutableList())
                val mLayoutManager = LinearLayoutManager(context)
                recyclerView.layoutManager = mLayoutManager
                recyclerView.itemAnimator = DefaultItemAnimator()
                recyclerView.invalidateItemDecorations()
                recyclerView.adapter = mAdapter
            }else{
                Toast.makeText(context,"No data available to show", Toast.LENGTH_SHORT).show()
            }
        }
    }

}