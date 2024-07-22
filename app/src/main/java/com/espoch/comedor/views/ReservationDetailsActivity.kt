package com.espoch.comedor.views

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.espoch.comedor.R

class ReservationDetailsActivity : AppCompatActivity() {
    private lateinit var rvReservation: RecyclerView
    //private lateinit var reservationAdapter : ReservationAdapterFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_reservation_details)

        rvReservation = findViewById(R.id.rvReservations)
        //setupRecyclerView()
        setupBottomNavigation()
    }

/*   private fun setupRecyclerView(){
        reservationAdapter = ReservationAdapterFragment(getReservations())
        rvReservation.layoutManager = LinearLayoutManager(this)
        rvReservation.adapter = reservationAdapter
    }

    private fun getReservations(): List<Reservation> {
        // Replace this with your actual data retrieval logic
        return PreferencesUtilFragment.getReservation(this)
    }
*/
    private fun setupBottomNavigation(){
        findViewById<ImageButton>(R.id.navigation_home).setOnClickListener {/*Navegacion al inicio*/}
        findViewById<ImageButton>(R.id.navigation_mapa).setOnClickListener {/*Navegacion a favoritos*/}
        findViewById<ImageButton>(R.id.navigation_booking).setOnClickListener {/*Navegacion al calendario*/}
        findViewById<ImageButton>(R.id.navigation_profile).setOnClickListener {/*Navegacion al perfil*/}
    }
}
