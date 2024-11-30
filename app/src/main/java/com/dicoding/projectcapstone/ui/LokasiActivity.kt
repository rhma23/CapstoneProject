package com.dicoding.projectcapstone.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dicoding.projectcapstone.R;
import com.dicoding.projectcapstone.model.Lokasi;
import com.dicoding.projectcapstone.ui.adapter.LokasiAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;

public class LokasiActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi);

        // Inisialisasi Toolbar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Inisialisasi MapView
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        // Inisialisasi RecyclerView
        recyclerView = findViewById(R.id.recycler_view_places);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Data Dummy
        ArrayList<Lokasi> lokasiList = new ArrayList<>();
        lokasiList.add(new Lokasi("Restoran A", "1.5 km", "15 menit", R.drawable.ic_launcher_background, 4.5));
        lokasiList.add(new Lokasi("Cafe B", "2.0 km", "20 menit", R.drawable.ic_launcher_background, 4.0));

        LokasiAdapter adapter = new LokasiAdapter(lokasiList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        LatLng lokasiPengguna = new LatLng(-6.2088, 106.8456); // Jakarta
        googleMap.addMarker(new MarkerOptions().position(lokasiPengguna).title("Lokasi Anda"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lokasiPengguna, 15));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}