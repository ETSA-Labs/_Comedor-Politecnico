package com.espoch.comedor

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.espoch.comedor.databinding.ActivityMainBinding
import com.espoch.comedor.models.AppUser
import com.espoch.comedor.services.AuthService
import com.espoch.comedor.services.FirebaseService
import com.espoch.comedor.services.NavigationService
import com.espoch.comedor.views.ReservationDetailsActivity
import com.espoch.comedor.views.ReservationFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        window.navigationBarColor = getColor(R.color.navigationBar)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.app_host_fragment) as NavHostFragment
        val navView = binding.bottomNavView
        val navCtrl = navHost.navController

        navView.setupWithNavController(navCtrl)

        NavigationService.register("App", navCtrl)

        AuthService.initialize(this)
        AuthService.addResultListener(AuthResultCallback())

        FirebaseService.initialize(this)
        FirebaseService.addResultListener(FirebaseResultCallback())

        onBackPressedDispatcher.addCallback(this, OnBackInvokedCallback())
    }

    private fun requestSignIn() {
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
    }

    private inner class AuthResultCallback : AuthService.ResultListener() {
        override fun onCreate() {
            super.onCreate()

            if (!AuthService.isSignedIn)
                this@MainActivity.requestSignIn()
        }

        override fun onSignIn() {
            super.onSignIn()

            // SignIn in Firebase, as Guest, but its better than nothing.
            FirebaseService.signIn()
        }

        override fun onSignOut() {
            super.onSignOut()

            requestSignIn()
        }
    }

    private inner class FirebaseResultCallback : FirebaseService.ResultListener() {
        override fun onSuccess() {
            super.onSuccess()

            FirebaseService.Users.get(AppUser.current.uid,
                object: FirebaseService.FirestoreResult<AppUser>()
                {
                    override fun onComplete(value: AppUser?) {
                        super.onComplete(value)

                        if (value is AppUser)
                            AppUser.current.role = value.role
                        else
                            FirebaseService.Users.add(AppUser.current)
                    }
                })
        }
    }

    private inner class OnBackInvokedCallback : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finishAffinity()
        }
    }
}

 /*
 * navView.setOnItemSelectedListener { item ->
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
        }*/