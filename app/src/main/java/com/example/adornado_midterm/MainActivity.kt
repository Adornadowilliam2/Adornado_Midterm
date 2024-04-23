package com.example.adornado_midterm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.adornado_midterm.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfig: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNav: BottomNavigationView = binding.bottomNavigationView

        val navHost = findNavController(R.id.navHost)

        appBarConfig = AppBarConfiguration(
            setOf(
                //all of the top level fragments
                R.id.landingPage,
                R.id.guideLine
            )
        )
        //hide bottom nav if fragment is not top-level
        navHost.addOnDestinationChangedListener{
                _: NavController, destination: NavDestination, _:Bundle? ->

            bottomNav.isVisible = appBarConfig.topLevelDestinations.contains(destination.id)
        }


        bottomNav.setupWithNavController(navHost)

    }
}