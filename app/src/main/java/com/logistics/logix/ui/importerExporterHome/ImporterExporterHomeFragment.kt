package com.logistics.logix.ui.importerExporterHome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.logistics.logix.R
import com.logistics.logix.ui.freightForwarderHome.FreightForwarderHomeViewModel

class ImporterExporterHomeFragment  : Fragment() {

    private lateinit var importerExporterHomeViewModel: ImporterExporterHomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        importerExporterHomeViewModel =
            ViewModelProviders.of(this).get(ImporterExporterHomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_importer_exporter_home, container, false)
        //val textView: TextView = root.findViewById(R.id.text_home)

        return root
    }

}