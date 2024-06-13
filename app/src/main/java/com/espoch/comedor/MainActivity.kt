package com.espoch.comedor

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.espoch.comedor.databinding.ActivityMainBinding
import com.espoch.comedor.services.AuthService
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.microsoft.identity.client.IAuthenticationResult
import com.microsoft.identity.client.exception.MsalException

class MainActivity : AppCompatActivity(), AuthService.ResultListener {

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

        AuthService.initialize(this)
        AuthService.addResultListener(this)
    }

    override fun onCreate() {
        //AuthService.signIn()
    }

    override fun onSuccess(result: IAuthenticationResult?) {

    }

    override fun onSignOut() {

    }

    override fun onCancel() {

    }

    override fun onError(exception: MsalException?) {
        Log.d("MSAL", exception?.message.toString())
    }
}