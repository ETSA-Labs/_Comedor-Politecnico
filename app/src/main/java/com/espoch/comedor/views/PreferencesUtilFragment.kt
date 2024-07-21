package com.espoch.comedor.views

import android.content.Context
import android.content.SharedPreferences
import com.espoch.comedor.models.Reservation
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object PreferencesUtilFragment{
    private const val PREF_NAME = "ReservationPrefs"
    private const val KEY_RESERVATION = "reservations"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveReservation(context: Context, reservation: List<Reservation>) {
        val editor = getPreferences(context).edit()
        val json = Gson().toJson(reservation)
        editor.putString(KEY_RESERVATION, json)
        editor.apply()
    }

    fun getReservation(context: Context): List<Reservation> {
        val json = getPreferences(context).getString(KEY_RESERVATION, null)
        return if (json != null) {
            val type = object : TypeToken<List<Reservation>>() {}.type
            Gson().fromJson(json, type)
        } else {
            emptyList()
        }
    }

}