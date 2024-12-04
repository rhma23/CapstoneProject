package com.dicoding.projectcapstone.ui.kategori

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.projectcapstone.R
import com.dicoding.projectcapstone.model.Minuman
import com.dicoding.projectcapstone.ui.adapter.MinumanAdapter

class KategoriMinumanActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategori_minuman)

        recyclerView = findViewById(R.id.recycler_view_kategori_drink)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val minumanList = listOf(
            Minuman("Es Teh", "Es teh manis segar", "Rp 5.000", R.drawable.ic_launcher_background),
            Minuman("Jus Jeruk", "Jus jeruk segar", "Rp 10.000", R.drawable.ic_launcher_background)
        )

        val adapter = MinumanAdapter(minumanList)
        recyclerView.adapter = adapter
    }
}
