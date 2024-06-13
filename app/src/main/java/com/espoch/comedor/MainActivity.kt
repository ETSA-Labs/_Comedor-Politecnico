package com.espoch.comedor

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.espoch.comedor.databinding.ActivityMainBinding
import com.espoch.comedor.views.BreakfastFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navView: BottomNavigationView
    private lateinit var navCtrl: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.app_host_fragment) as NavHostFragment
        navView = binding.bottomNavView
        navCtrl = navHost.navController

        navView.setupWithNavController(navCtrl)

        setContentView(R.layout.activity_main)

        button_desayunos.setOnClickListener {
            supportFragmentManager.commit {
                replace(R.id.fragment_container, BreakfastFragment())
            }
        }
    }

}