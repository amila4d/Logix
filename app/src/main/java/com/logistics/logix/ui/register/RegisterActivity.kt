package com.logistics.logix.ui.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch.LimitExceedListener
import com.androidbuts.multispinnerfilter.SpinnerListener
import com.logistics.logix.R
import com.logistics.logix.database.model.User
import com.logistics.logix.database.model.UserType
import com.logistics.logix.ui.BaseActivity
import com.logistics.logix.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : BaseActivity() {

    private var btnRegister: Button? = null

    internal lateinit var context: Context
    private lateinit var registerViewModel: RegisterViewModel

    private var selectedUserTypeNameList: MutableList<String>? = null
    private var selectedUserTypeIdList: MutableList<Int>? = null
    private var selectedUserType: String? = ""
    private var selectedUserTypeId: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        context = this
        registerViewModel =
            ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        setupActionBar()
        initWidgets()
    }

    private fun setupActionBar() {
        val actionBar = supportActionBar
        if (actionBar != null) {
            setActionBar(context, actionBar, getString(R.string.welcome_to_logix), 24f)
            displayHomeAsUpEnabled(actionBar, false)
        }
    }

    private fun initWidgets() {
        btnRegister = findViewById(R.id.btnRegister)
        etFullName.text

        etFullName!!.addTextChangedListener(LoginTextWatcher(etFullName!!))
        etEmail!!.addTextChangedListener(LoginTextWatcher(etEmail!!))
        etEmail!!.addTextChangedListener(LoginTextWatcher(etEmail!!))
        etPassword!!.addTextChangedListener(LoginTextWatcher(etPassword!!))
        etConfirmPw!!.addTextChangedListener(LoginTextWatcher(etConfirmPw!!))


        val userType1 = UserType(1,"Freight forwarder")
        val userType2 = UserType(2,"Shipping company")
        val userType3 = UserType(3,"Importer/ exporter")
        val userTypesList: MutableList<UserType>? = arrayListOf()
        userTypesList?.add(userType1)
        userTypesList?.add(userType2)
        userTypesList?.add(userType3)

        val listArray0 = java.util.ArrayList<KeyPairBoolData>()
        for (i in 0 until userTypesList?.size!!) {
            val h = KeyPairBoolData()
            h.id = userTypesList[i].id.toLong()
            h.name = userTypesList[i].userType
            h.isSelected = false
            listArray0.add(h)
        }

        multiSelectSpinnerCustomerType!!.setItems(listArray0, -1, SpinnerListener { items ->
            selectedUserTypeNameList = arrayListOf()
            selectedUserTypeIdList = arrayListOf()
            for (i in items.indices) {
                if (items[i].isSelected) {
                    selectedUserTypeNameList!!.add(items[i].name)
                    selectedUserTypeIdList!!.add(items[i].id.toInt())
                }
            }
            if(selectedUserTypeNameList!!.size > 0){
                selectedUserType = selectedUserTypeNameList!![0]
                selectedUserTypeId = selectedUserTypeIdList!![0]
            }
        })

        multiSelectSpinnerCustomerType.setLimit(1, LimitExceedListener {
            Toast.makeText(
                applicationContext,
                "Limit exceed ", Toast.LENGTH_LONG
            ).show()
        })

        btnRegister!!.setOnClickListener {
            if (submitForm()) {
                var active: Int = 0
                if(selectedUserTypeId == 3) {
                    active = 1
                }else{
                    active = 0
                }
                registerViewModel.insertUser(
                    User(0,
                        etEmail.text.toString(),
                        etFullName.text.toString(),
                        etPassword.text.toString(),
                        selectedUserType!!,
                        selectedUserTypeId!!,
                        active) )
                val intent = Intent(this, LoginActivity::class.java).apply {
                    putExtra("msg", "msg")
                }
                startActivity(intent)
                finish()
            }
        }
    }

    private fun submitForm(): Boolean {
        if (!validateFullName()) {
            return false
        }
        if (!validateEmail()) {
            return false
        }
        if (!validatePassword()) {
            return false
        }
        if (!validateConfirmPassword()) {
            return false
        }
        if(selectedUserTypeIdList == null || selectedUserTypeIdList!!.size == 0){
            Toast.makeText(context,"Please select user type", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private inner class LoginTextWatcher internal constructor(private val view: View) :
        TextWatcher {

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun afterTextChanged(editable: Editable) {
            when (view.id) {
                R.id.etFullName -> validateFullName()
                R.id.etEmail -> validateEmail()
                R.id.etPassword -> validatePassword()
                R.id.etConfirmPw -> validateConfirmPassword()
            }
        }
    }

    private fun validateEmail(): Boolean {
        if (etEmail!!.text.toString().trim().isEmpty() && etEmail!!.text!!.isNotBlank()) {
            textInputEmail!!.error = getString(R.string.please_type_email)
            requestFocus(etEmail!!)
            return false
        } else if (etEmail!!.text!!.length < 4) {
            textInputEmail!!.error = getString(R.string.email_min_length)
            requestFocus(etEmail!!)
            return false
        } else {
            textInputEmail!!.isErrorEnabled = false
        }

        return true
    }

    private fun validateFullName(): Boolean {
        if (etFullName!!.text.toString().trim().isEmpty()) {
            textInputFullName!!.error = getString(R.string.please_type_full_name)
            requestFocus(etFullName!!)
            return false
        } else {
            textInputFullName!!.isErrorEnabled = false
        }
        return true
    }

    private fun validatePassword(): Boolean {
        if (etPassword!!.text.toString().trim().isEmpty()) {
            textInputPassword!!.error = getString(R.string.please_type_password)
            requestFocus(etPassword!!)
            return false
        } else {
            textInputPassword!!.isErrorEnabled = false
        }
        return true
    }

    private fun validateConfirmPassword(): Boolean {
        if (etConfirmPw!!.text.toString().trim().isEmpty()) {
            textInputConfirmPw!!.error = getString(R.string.please_type_password_again)
            requestFocus(etConfirmPw!!)
            return false
        }else if(etPassword!!.text.toString().trim().isNotEmpty() &&
            etPassword!!.text.toString() != etConfirmPw!!.text.toString()
        ){
            textInputConfirmPw!!.error = "Password did not match"
            requestFocus(etConfirmPw!!)
            return false
        }
        else {
            textInputConfirmPw!!.isErrorEnabled = false
        }
        return true
    }

    private fun requestFocus(view: View) {
        if (view.requestFocus()) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
    }

}