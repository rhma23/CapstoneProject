package com.dicoding.projectcapstone.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.projectcapstone.R
import com.dicoding.projectcapstone.model.Lokasi
import com.dicoding.projectcapstone.ui.adapter.LokasiAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LokasiActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mapView: MapView
    private lateinit var recyclerView: RecyclerView
    private var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lokasi)

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
        lokasiList.add(
            Lokasi(
                "Restoran A", "1.5 km", "15 menit", R.drawable.ic_launcher_background, 4.5, true))
        lokasiList.add(
            Lokasi("Cafe B", "2.0 km", "20 menit", R.drawable.ic_launcher_background, 4.0, false))

        val adapter = LokasiAdapter(lokasiList)
        recyclerView.setAdapter(adapter)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        val lokasiPengguna = LatLng(-6.2088, 106.8456) // Jakarta
        googleMap!!.addMarker(MarkerOptions().position(lokasiPengguna).title("Lokasi Anda"))
        googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(lokasiPengguna, 15f))
    }

    override fun onResume() {
        super.onResume()
        mapView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView!!.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView!!.onSaveInstanceState(outState)
    }
}