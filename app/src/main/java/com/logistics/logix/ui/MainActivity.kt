package com.logistics.logix.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.iid.FirebaseInstanceId
import com.logistics.logix.R
import com.logistics.logix.database.ModelPreferences
import com.logistics.logix.database.model.User
import com.logistics.logix.ui.freightForwarderHome.FreightForwarderHomeFragment
import com.logistics.logix.ui.login.LoginActivity
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named


class MainActivity : BaseActivity() , NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mainViewModel: MainViewModel

    private val modelPreferences: ModelPreferences = get()

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navUsername: TextView
    private lateinit var navUserEmail: TextView
    private lateinit var navView: NavigationView
    private lateinit var navController: NavController
    private lateinit var user: User
    private val homeFragment: Fragment = FreightForwarderHomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        mainViewModel =
            ViewModelProviders.of(this).get(MainViewModel::class.java)
        user = modelPreferences.getObject("user", User::class.java)!!
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home_freight_forwarder_home,
            R.id.nav_search,
            R.id.nav_notification,
            R.id.nav_consignment_details,
            R.id.nav_chat,
            R.id.nav_settings
        ), drawerLayout)


        val navMenu: Menu = navView.menu
        var navGraph = navController.graph
        if(user.userTypeId == 1) {
            navMenu.findItem(R.id.nav_home_freight_forwarder_home).isVisible = true
            navGraph.startDestination = R.id.nav_home_freight_forwarder_home
            navMenu.findItem(R.id.nav_home_shipping_container_company_home).isVisible = false
            navMenu.findItem(R.id.nav_home_importer_exporter_home).isVisible = false
            navMenu.findItem(R.id.nav_consignment_details).isVisible = false
        }else if(user.userTypeId == 2){
            navMenu.findItem(R.id.nav_home_freight_forwarder_home).isVisible = false
            navMenu.findItem(R.id.nav_home_shipping_container_company_home).isVisible = true
            navGraph.startDestination = R.id.nav_home_shipping_container_company_home
            navMenu.findItem(R.id.nav_home_importer_exporter_home).isVisible = false
            navMenu.findItem(R.id.nav_search).isVisible = false
            appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home_shipping_container_company_home,
                R.id.nav_search,
                R.id.nav_notification,
                R.id.nav_consignment_details,
                R.id.nav_chat,
                R.id.nav_settings
            ), drawerLayout)
        }else if(user.userTypeId == 3){
            navMenu.findItem(R.id.nav_home_freight_forwarder_home).isVisible = false
            navMenu.findItem(R.id.nav_home_shipping_container_company_home).isVisible = false
            navMenu.findItem(R.id.nav_consignment_details).isVisible = false
            navMenu.findItem(R.id.nav_home_importer_exporter_home).isVisible = true
            navMenu.findItem(R.id.nav_search).isVisible = false
            navMenu.findItem(R.id.nav_notification).isVisible = false
            navGraph.startDestination = R.id.nav_home_importer_exporter_home
            appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home_importer_exporter_home,
                R.id.nav_search,
                R.id.nav_notification,
                R.id.nav_consignment_details,
                R.id.nav_chat,
                R.id.nav_settings
            ), drawerLayout)
        }
        navController.graph = navGraph

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(this)

        val headerView : View = navView.getHeaderView(0)
        navUsername = headerView.findViewById(R.id.tvUserFullName)
        navUserEmail = headerView.findViewById(R.id.tvUserEmail)
        navUsername.text = user.fullName
        navUserEmail.text = user.email +" | "+user.userType
        initFireBase()

        drawerLayout.addDrawerListener(object : DrawerListener {
            override fun onDrawerSlide(
                drawerView: View,
                slideOffset: Float
            ) {
            }

            override fun onDrawerOpened(drawerView: View) {

            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerStateChanged(newState: Int) {
                user = modelPreferences.getObject("user", User::class.java)!!
                navUsername.text = user.fullName
                navUserEmail.text = user.email +" | "+user.userType
            }
        })
    }

    private fun initFireBase() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }
                val fcmToken = task.result!!.token
                Log.e("FCM TOKEN: ",fcmToken)
            })
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_home_freight_forwarder_home -> {

                if(user.userTypeId == 1) {
                    navController.navigate(R.id.nav_home_freight_forwarder_home)
                }else if(user.userTypeId == 2){
                    navController.navigate(R.id.nav_home_shipping_container_company_home)
                }else if(user.userTypeId == 3){
                    navController.navigate(R.id.nav_home_importer_exporter_home)
                }

            }
            R.id.nav_home_shipping_container_company_home -> {
                navController.navigate(R.id.nav_home_shipping_container_company_home)
            }
            R.id.nav_home_importer_exporter_home -> {
                navController.navigate(R.id.nav_home_importer_exporter_home)
            }
            R.id.nav_search -> {


                if(user.userTypeId == 1) {
                    navController.popBackStack(R.id.nav_home_freight_forwarder_home, true)
                }else if(user.userTypeId == 2){
                    navController.popBackStack(R.id.nav_home_shipping_container_company_home, true)
                }else if(user.userTypeId == 3){
                    navController.popBackStack(R.id.nav_home_importer_exporter_home, true)
                }
                navController.navigate(R.id.nav_search)
                //navController.popBackStack(R.id.nav_home, true)
                //Toast.makeText(this, "Android Store", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_notification -> {
                if(user.userTypeId == 1) {
                    navController.popBackStack(R.id.nav_home_freight_forwarder_home, true)
                }else if(user.userTypeId == 2){
                    navController.popBackStack(R.id.nav_home_shipping_container_company_home, true)
                }else if(user.userTypeId == 3){
                    navController.popBackStack(R.id.nav_home_importer_exporter_home, true)
                }
                navController.navigate(R.id.nav_notification)
            }
            R.id.nav_consignment_details ->{
                if(user.userTypeId == 1) {
                    navController.popBackStack(R.id.nav_home_freight_forwarder_home, true)
                }else if(user.userTypeId == 2){
                    navController.popBackStack(R.id.nav_home_shipping_container_company_home, true)
                }else if(user.userTypeId == 3){
                    navController.popBackStack(R.id.nav_home_importer_exporter_home, true)
                }
                navController.navigate(R.id.nav_consignment_details)
            }
            R.id.nav_chat -> {
                if(user.userTypeId == 1) {
                    navController.popBackStack(R.id.nav_home_freight_forwarder_home, true)
                }else if(user.userTypeId == 2){
                    navController.popBackStack(R.id.nav_home_shipping_container_company_home, true)
                }else if(user.userTypeId == 3){
                    navController.popBackStack(R.id.nav_home_importer_exporter_home, true)
                }
                navController.navigate(R.id.nav_chat)
            }
            R.id.nav_settings -> {
                if(user.userTypeId == 1) {
                    navController.popBackStack(R.id.nav_home_freight_forwarder_home, true)
                }else if(user.userTypeId == 2){
                    navController.popBackStack(R.id.nav_home_shipping_container_company_home, true)
                }else if(user.userTypeId == 3){
                    navController.popBackStack(R.id.nav_home_importer_exporter_home, true)
                }
                navController.navigate(R.id.nav_settings)
            }
            R.id.nav_signout -> {
                val sharedPref: SharedPreferences by inject(named(getString(R.string.settings_prefs)))
                val editor: SharedPreferences.Editor =  sharedPref.edit()
                editor.putBoolean(getString(R.string.is_logged_in), false)
                editor.apply()
                editor.commit()

                val intent = Intent(this, LoginActivity::class.java).apply {
                    putExtra("msg", "msg")
                }
                startActivity(intent)
                finish()
                Toast.makeText(this, "Successfully logout from Logix", Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return false
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else
            super.onBackPressed()
    }

}
