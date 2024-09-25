package com.umut.appsport

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController


import com.google.firebase.auth.FirebaseAuth
import com.umut.appsport.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var selectedLeague:String
    private  var selectedYear:Int=2023

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        setupToolbar()
        setupNavigation()

        selectedLeague="Premier League"
        selectedYear=2023

        setupUserDetails()
    }


    private fun setupToolbar() {
        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)

    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.home2, R.id.fixtureFragment, R.id.nav_standings, R.id.nav_logout),
            binding.drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            val bundle = Bundle().apply {
                putString("selectedLeague", selectedLeague)
                putInt("selectedYear", selectedYear)
            }

            when (menuItem.itemId) {
                R.id.nav_home -> {
                    navController.navigate(R.id.home2, bundle)
                }
                R.id.nav_fixtures -> {
                    navController.navigate(R.id.fixtureFragment, bundle)
                }
                R.id.nav_top_goals -> {
                    navController.navigate(R.id.topScorersFragment, bundle)
                }
                R.id.nav_top_assists -> {
                    navController.navigate(R.id.topAssistsFragment, bundle)
                }
                R.id.nav_standings -> {
                    navController.navigate(R.id.standingsFragment, bundle)
                }
                R.id.nav_logout -> {
                    logout()
                }
            }
            binding.drawerLayout.closeDrawers()
            true
        }
    }
    private fun setupUserDetails() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val email = user.email
            val username = email?.substringBefore("@")

            val navigationView = binding.navView
            val headerView = navigationView.getHeaderView(0)
            val navHeaderTitle = headerView.findViewById<TextView>(R.id.nav_header_title)
            navHeaderTitle.text = username
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        navController.navigate(R.id.blank)
    }
    fun updateSelectedLeague(league: String) {
        selectedLeague = league
    }

    fun updateSelectedYear(year: Int) {
        selectedYear = year
    }



}
