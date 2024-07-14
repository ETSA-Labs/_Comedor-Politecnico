package com.espoch.comedor

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.espoch.comedor.views.MoreFragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.PolyUtil
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import kotlin.concurrent.thread

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var currentLatLng: LatLng
    private lateinit var targetLatLng: LatLng
    private lateinit var polyline: Polyline
    private lateinit var directionText: TextView
    private lateinit var nextStepTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var distanceTextView: TextView
    private lateinit var steps: JSONArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.supportfragment)

        directionText = findViewById(R.id.directionText)
        nextStepTextView = findViewById(R.id.nextStepTextView)
        timeTextView = findViewById(R.id.timeTextView)
        distanceTextView = findViewById(R.id.distanceTextView)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    currentLatLng = LatLng(location.latitude, location.longitude)
                    mMap.clear()
                    mMap.addMarker(MarkerOptions().position(currentLatLng).title("Ubicación actual"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                    targetLatLng = LatLng(-1.657711, -78.675287)
                    drawRoute(currentLatLng, targetLatLng)
                }
            }
        }

        // Configurar el OnClickListener para el botón de retroceso
        findViewById<Button>(R.id.button2).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MoreFragment()) // Asegúrate de tener un contenedor para los fragmentos
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1)
            return
        }

        mMap.isMyLocationEnabled = true
        targetLatLng = LatLng(-1.657711, -78.675287)
        mMap.addMarker(MarkerOptions().position(targetLatLng).title("Destino"))

        startLocationUpdates()
    }

    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1)
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun drawRoute(start: LatLng, end: LatLng) {
        val apiKey = getString(R.string.API_KEY)
        val url = "https://maps.googleapis.com/maps/api/directions/json?origin=${start.latitude},${start.longitude}&destination=${end.latitude},${end.longitude}&key=$apiKey&mode=driving&language=es&units=metric"

        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let {
                    val jsonResponse = JSONObject(it)
                    val routes = jsonResponse.getJSONArray("routes")
                    if (routes.length() > 0) {
                        val points = routes.getJSONObject(0).getJSONObject("overview_polyline").getString("points")
                        val leg = routes.getJSONObject(0).getJSONArray("legs").getJSONObject(0)
                        steps = leg.getJSONArray("steps")
                        runOnUiThread {
                            drawPolyline(points)
                            updateDirections(steps, leg)
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(this@MapsActivity, "No se encontraron rutas", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        })
    }

    private fun drawPolyline(encodedPath: String) {
        val path = PolylineOptions().addAll(PolyUtil.decode(encodedPath)).color(android.graphics.Color.BLUE).width(5f)
        polyline = mMap.addPolyline(path)
    }

    private fun updateDirections(steps: JSONArray, leg: JSONObject) {
        if (steps.length() > 0) {
            val firstStep = steps.getJSONObject(0)
            val htmlInstructions = firstStep.getString("html_instructions")

            if (steps.length() > 1) {
                val nextStep = steps.getJSONObject(1)
                val nextHtmlInstructions = nextStep.getString("html_instructions")
                nextStepTextView.text = nextHtmlInstructions.replace(Regex("<[^>]*>"), "")
            }

            val duration = leg.getJSONObject("duration").getString("text")
            val distance = leg.getJSONObject("distance").getString("text")
            timeTextView.text = duration
            distanceTextView.text = distance
        }
    }

    private fun simulateNavigation(steps: JSONArray) {
        thread {
            for (i in 0 until steps.length()) {
                val step = steps.getJSONObject(i)
                val polylinePoints = PolyUtil.decode(step.getJSONObject("polyline").getString("points"))
                for (point in polylinePoints) {
                    runOnUiThread {
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(point))
                        updateDirections(step.getJSONArray("steps"), step)
                    }
                    Thread.sleep(1000) // Espera 1 segundo antes de mover al siguiente punto
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.isMyLocationEnabled = true
                    startLocationUpdates()
                }
            }
        }
    }
}
