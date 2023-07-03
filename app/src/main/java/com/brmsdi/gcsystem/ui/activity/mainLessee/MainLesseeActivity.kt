package com.brmsdi.gcsystem.ui.activity.mainLessee

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
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.listeners.OnSearchViewListener
import com.brmsdi.gcsystem.databinding.ActivityMainLesseeBinding

class MainLesseeActivity : AppCompatActivity(), OnSearchViewListener {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainLesseeBinding
    private lateinit var searchView: SearchView
    private lateinit var onQueryTextListener : SearchView.OnQueryTextListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainLesseeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayoutLessee
        val navView: NavigationView = binding.navViewLessee
        val navController = findNavController(R.id.nav_host_fragment_content_main_lessee)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_my_account_lessee, R.id.nav_repair_request
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        if (!::onQueryTextListener.isInitialized) throw RuntimeException(this.applicationContext.toString() + " must implement SearchViewListener")
        searchView.setOnQueryTextListener(onQueryTextListener)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main_lessee)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun addSearchListener(listener : SearchView.OnQueryTextListener) {
        this.onQueryTextListener = listener
    }
}