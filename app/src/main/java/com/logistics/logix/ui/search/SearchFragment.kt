package com.logistics.logix.ui.search

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.logistics.logix.R
import com.logistics.logix.database.ModelPreferences
import com.logistics.logix.database.model.Search
import com.logistics.logix.database.model.User
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.get
import java.text.SimpleDateFormat
import java.util.*

class SearchFragment : Fragment() {

    private val modelPreferences: ModelPreferences = get()

    private lateinit var searchViewModel: SearchViewModel

    private var etDate: EditText? = null
    private var btnSearch: Button? = null
    private var selectedDate: String = ""
    internal lateinit var context: Context
    private lateinit var user: User

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        context = this.activity!!
        initializations(root)
        return root
    }


    private fun initializations(view: View){
        etDate = view.findViewById(R.id.etDate)
        btnSearch = view.findViewById(R.id.btnSearch)
        user = modelPreferences.getObject("user", User::class.java)!!

        val cal = Calendar.getInstance()
        val df = SimpleDateFormat("yyyy-MM-dd")
        cal.add(Calendar.DATE, 0)
        selectedDate = df.format(cal.time)


        etDate?.setOnClickListener {
            val cal = Calendar.getInstance()
            if(selectedDate.isNotEmpty()) {
                val sdf = SimpleDateFormat("yyyy-MM-dd")
                val date = sdf.parse(selectedDate)
                cal.time = date
            }

            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            val dpdTo = DatePickerDialog(context,
                DatePickerDialog.OnDateSetListener { view, year, month,
                                                     day ->
                    selectedDate = "" + year + "-" + (month+1) + "-" + day
                    etDate!!.setText(getCurrentDate(selectedDate))
                }, year, month, day)

            dpdTo.datePicker.minDate = System.currentTimeMillis() - 1000
            dpdTo.show()
        }

        btnSearch!!.setOnClickListener {
            if(etPreferredCompany.text.toString().isNotEmpty() &&
                etSizeOfContainer.text.toString().isNotEmpty() &&
                etGoodsType.text.toString().isNotEmpty() &&
                etDate!!.text.toString().isNotEmpty() &&
                    user.userTypeId != null) {
                val search = Search(
                    0, etPreferredCompany.text.toString(),
                    etSizeOfContainer.text.toString().toInt(),
                    etGoodsType.text.toString(),
                    etDate!!.text.toString(),
                    user.userTypeId,
                    user.id
                )
                searchViewModel.insertSearch(search)
                showAlert()
            }else{
                Toast.makeText(context, "Please enter all search details",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Form Submitted!")
        builder.setMessage("We will get back to you shortly with your container..")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Ok"){ dialogInterface, which ->
            findNavController().popBackStack(R.id.nav_search, true);
            findNavController().navigate(R.id.nav_home_freight_forwarder_home)
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun getCurrentDate(dateAndTme: String): String {
        val df = SimpleDateFormat("yyyy-M-d", Locale.US)
        val result: Date
        result = df.parse(dateAndTme)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return sdf.format(result)
    }

}
