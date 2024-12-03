package com.dicoding.projectcapstone.ui.kategori

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.projectcapstone.R
import com.dicoding.projectcapstone.model.Makanan
import com.dicoding.projectcapstone.ui.adapter.MakananAdapter

class KategoriMakananActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategori_makanan)


        recyclerView = findViewById(R.id.recycler_view_kategori_food)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val makananList = listOf(
            Makanan("Nasi Goreng", "Nasi goreng lezat dengan telur", "Rp 20.000", R.drawable.ic_launcher_background),
            Makanan("Mie Goreng", "Mie goreng dengan sayuran", "Rp 18.000", R.drawable.ic_launcher_background)
        )

        val adapter = MakananAdapter(makananList)
        recyclerView.adapter = adapter
    }
}
