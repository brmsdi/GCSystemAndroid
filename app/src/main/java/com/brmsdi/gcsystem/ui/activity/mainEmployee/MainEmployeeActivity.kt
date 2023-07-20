package com.brmsdi.gcsystem.ui.activity.mainEmployee

import android.os.Bundle
import android.view.Menu
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.navigation.NavController
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.listeners.OnSearchViewListener
import com.brmsdi.gcsystem.databinding.ActivityMainEmployeeBinding

class MainEmployeeActivity : AppCompatActivity(), OnSearchViewListener {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainEmployeeBinding
    private lateinit var onQueryTextListener: OnQueryTextListener
    private lateinit var navController: NavController
    private lateinit var searchView: SearchView
    private lateinit var searchable : HashSet<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainEmployeeBinding.inflate(layoutInflater)
        searchable = hashSetOf(R.id.nav_order_service)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMainEmployee.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayoutEmployee
        val navView: NavigationView = binding.navViewEmployee
        navController = findNavController(R.id.nav_host_fragment_content_main_employee)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_my_account_employee, R.id.nav_order_service
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        onFocusSearchView(searchView)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            isSearchable(searchItem, destination.id, searchable)
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        //val navController = findNavController(R.id.nav_host_fragment_content_main_employee)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun addSearchListener(listener: OnQueryTextListener) {
        this.onQueryTextListener = listener
    }

    override fun onFocusSearchView(searchView: SearchView) {
        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus && ::onQueryTextListener.isInitialized) {
                searchView.setOnQueryTextListener(onQueryTextListener)
            }
        }
    }

}