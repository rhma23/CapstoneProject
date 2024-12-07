package com.dicoding.projectcapstone.location

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.projectcapstone.MainActivity
import com.dicoding.projectcapstone.profile.ProfileActivity
import com.dicoding.projectcapstone.R
import com.dicoding.projectcapstone.api.RetrofitClient.apiService
import com.dicoding.projectcapstone.databinding.ActivityLokasiBinding
import com.dicoding.projectcapstone.location.model.Lokasi
import com.dicoding.projectcapstone.location.adapter.LokasiAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.math.log

class LokasiActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityLokasiBinding
    private lateinit var mapView: MapView
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: LocationModel
    private val locationPermissionCode = 100
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLokasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Inisialisasi MapView
        mapView = findViewById(R.id.map_view)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        // Inisialisasi RecyclerView
        recyclerView = findViewById(R.id.recycler_view_places)
        recyclerView.setLayoutManager(LinearLayoutManager(this))

        // Data Dummy
        val lokasiList = ArrayList<Lokasi>()

        val adapter = LokasiAdapter(lokasiList)
        recyclerView.setAdapter(adapter)
        val repository = LocationRepository.getInstance(apiService)
        viewModel = ViewModelProvider(
            this,
            LocationModelFactory(repository)
        )[LocationModel::class.java]
        viewModel.locations.observe(this) { locations ->
            showMarkers(locations)
        }

        // Setup BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Set the default selected menu to "Location"
        bottomNavigationView.selectedItemId = R.id.location

        // Listener untuk navigasi
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    navigateWithLoading(MainActivity::class.java)
                    true
                }
                R.id.location -> {
                    navigateWithLoading(LokasiActivity::class.java)
                    true
                }
                R.id.profile -> {
                    navigateWithLoading(ProfileActivity::class.java)
                    true
                }
                else -> false
            }
        }

        checkLocationPermission()
        viewModel.getAllLocations()
    }

    private fun showMarkers(locations: List<LocationResponse>) {
        googleMap?.let { map ->
            fusedLocationClient.lastLocation.addOnSuccessListener { userLocation ->
                if (userLocation != null) {
                    val userLat = userLocation.latitude
                    val userLng = userLocation.longitude
                    val sortedLocations = locations.map { location ->
                        val distance = calculateDistance(
                            userLat, userLng,
                            location.latitude.toDouble(), location.longitude.toDouble()
                        )
                        Pair(location, distance)
                    }.sortedBy { it.second }
                    val nearestLocations = sortedLocations.take(5)
                    val adapter = recyclerView.adapter as LokasiAdapter
                    for (pair in nearestLocations) {
                        val location = pair.first
                        val distance = pair.second
                        val latLng = LatLng(location.latitude.toDouble(), location.longitude.toDouble())
                        map.addMarker(
                            MarkerOptions()
                                .position(latLng)
                                .title("${location.merchant.business_name} (${String.format("%.2f km", distance)})")
                        )
                        val isOpen = location.merchant.status.lowercase() == "buka"
                        Log.i(location.merchant.business_name, "showMarkers: "+isOpen)
                        val nearestLokasi = Lokasi(
                            location.merchant.business_name,
                            R.drawable.ic_launcher_background, //TODO: Get image, need change API Response from backend
                            location.merchant.average_rating,
                            isOpen,
                            distance
                        )
                        adapter.addLokasi(nearestLokasi)
                    }
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this, "Lokasi pengguna tidak tersedia", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap!!.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val lokasiPengguna = LatLng(location.latitude, location.longitude)
                    googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(lokasiPengguna, 15f))
                } else {
                    Toast.makeText(this, "Lokasi tidak tersedia", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                mapView.getMapAsync(this)
            } else {
                Toast.makeText(this, "Izin lokasi diperlukan untuk menggunakan fitur ini", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        }
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadius = 6371.0
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return earthRadius * c
    }

    private fun navigateWithLoading(targetActivity: Class<*>) {

        binding.loadingLokasi.visibility = View.VISIBLE // Tampilkan ProgressBar
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, targetActivity))
            binding.loadingLokasi.visibility = View.GONE // Sembunyikan ProgressBar
        }, 1500)
    }
}