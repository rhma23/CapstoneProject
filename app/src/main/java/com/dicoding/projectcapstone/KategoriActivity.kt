package com.dicoding.projectcapstone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class KategoriActivity {
    inner class kategori_activity : AppCompatActivity() {
        private val foodList: RecyclerView? = null
        private val kategoriList: ArrayList<Kategori>? = null
        private val kategoriAdapter: KategoriAdapter? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.item_kategori)

            //            foodList = findViewById(R.id.food_list);
//            foodList = findViewById(R.id.drink_list);
//
//            kategoriList = new ArrayList<>();
//            kategoriList.add(new Kategori("Makanan", R.drawable.ic_food));
//            kategoriList.add(new Kategori("Minuman", R.drawable.ic_drink));
//
//            kategoriAdapter = new KategoriAdapter(kategoriList);
//
//            foodList.setAdapter(kategoriAdapter);
//            foodList.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}
