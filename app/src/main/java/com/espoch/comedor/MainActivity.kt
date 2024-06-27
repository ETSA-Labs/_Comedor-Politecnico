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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        FirebaseApp.initializeApp(this)
        AuthService.initialize(this)
        AuthService.addResultListener(AuthResultCallback())

        onBackPressedDispatcher.addCallback(this, OnBackInvokedCallback())
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
            CoroutineScope(Dispatchers.Main).launch {
                Log.d("Auth", AppUser.default.accessToken)
                FirebaseService.signIn(this@MainActivity, AppUser.default.accessToken, AppUser.default.idToken)
            }
        }
    }

    private inner class OnBackInvokedCallback : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finishAffinity()
        }
    }
}