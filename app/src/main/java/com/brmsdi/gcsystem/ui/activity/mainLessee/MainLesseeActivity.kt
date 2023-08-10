package com.brmsdi.gcsystem.ui.activity.mainLessee

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.listeners.OnSearchViewListener
import com.brmsdi.gcsystem.databinding.ActivityMainLesseeBinding
import com.brmsdi.gcsystem.ui.activity.login.LoginActivity
import com.brmsdi.gcsystem.ui.activity.screenBiometric.ScreenAuthenticationActivity

class MainLesseeActivity : AppCompatActivity(), OnSearchViewListener {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainLesseeBinding
    private lateinit var onQueryTextListener: SearchView.OnQueryTextListener
    private lateinit var navController: NavController
    private lateinit var searchView: SearchView
    private lateinit var viewModel: MainLesseeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainLesseeBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainLesseeViewModel::class.java]
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))
        val drawerLayout: DrawerLayout = binding.drawerLayoutLessee
        val navView: NavigationView = binding.navViewLessee
        navController = findNavController(R.id.nav_host_fragment_content_main_lessee)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_my_account_lessee,
                R.id.nav_repair_request,
                R.id.nav_debts,
                R.id.nav_contracts
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener {
            if (it.itemId == R.id.nav_logout) {
                viewModel.logout()
                initLogin()
                finish()
            } else {
                NavigationUI.onNavDestinationSelected(it, navController)
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val searchable = hashSetOf(R.id.nav_repair_request)
        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        onFocusSearchView(searchView)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            isSearchable(searchItem, destination.id, searchable)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings_biometric -> {
                initSettingsBiometric()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun addSearchListener(listener: SearchView.OnQueryTextListener) {
        this.onQueryTextListener = listener
    }

    override fun onFocusSearchView(searchView: SearchView) {
        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus && ::onQueryTextListener.isInitialized) {
                searchView.setOnQueryTextListener(onQueryTextListener)
            }
        }
    }

    private fun initSettingsBiometric() {
        startActivity(Intent(this, ScreenAuthenticationActivity::class.java))
    }

    private fun initLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}