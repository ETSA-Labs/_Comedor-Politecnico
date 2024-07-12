package com.espoch.comedor

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.espoch.comedor.databinding.ActivityMainBinding
import com.espoch.comedor.services.AuthService
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navView: BottomNavigationView
    private lateinit var navCtrl: NavController

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        window.navigationBarColor = getColor(R.color.navigationBar)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.app_host_fragment) as NavHostFragment
        navView = binding.bottomNavView
        navCtrl = navHost.navController

        navView.setupWithNavController(navCtrl)

        //FirebaseApp.initializeApp(this)
        AuthService.initialize(this)
        AuthService.addResultListener(AuthResultCallback())

        onBackPressedDispatcher.addCallback(this, OnBackInvokedCallback())

        //LLAMADA A RESERVATIONSITEM
        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_booking -> {
                    // Abrir la actividad de reservas al seleccionar el ícono de reservas
                    val intent = Intent(this@MainActivity, ReservationConfirmationActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_profile -> {
                    // Navegar al fragmento de perfil al seleccionar el ícono de perfil
                    navCtrl.navigate(R.id.navigation_profile)
                    true
                }

                R.id.navigation_more -> {
                    // Navegar al fragmento de perfil al seleccionar el ícono de more
                    navCtrl.navigate(R.id.navigation_more)
                    true
                }
                R.id.navigation_home -> {
                    // Navegar al fragmento de perfil al seleccionar el ícono de perfil
                    navCtrl.navigate(R.id.navigation_home)
                    true
                }
                // Agregar otros casos si tienes más íconos en la barra de navegación
                else -> false
            }
        }

    }

    private inner class AuthResultCallback : AuthService.ResultListener() {
        override fun onCreate() {
            super.onCreate()

            if (!AuthService.isSignedIn) {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        override fun onSignIn() {
            super.onSignIn()

            // now sign in in Firebase
            //FirebaseService.signIn(this@MainActivity, AppUser.default.token)
        }
    }

    private inner class OnBackInvokedCallback : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finishAffinity()
        }
    }
}