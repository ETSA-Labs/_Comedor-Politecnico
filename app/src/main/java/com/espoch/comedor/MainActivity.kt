package com.espoch.comedor

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.espoch.comedor.databinding.ActivityMainBinding
import com.espoch.comedor.shared.models.AppUser
import com.espoch.comedor.shared.services.AuthService
import com.espoch.comedor.shared.services.FirebaseService
import com.espoch.comedor.shared.services.NavigationService
import com.espoch.comedor.R as R1
import com.espoch.comedor.shared.R as R2

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        window.navigationBarColor = getColor(R2.color.navigationBar)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R1.id.app_host_fragment) as NavHostFragment
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