package com.dicoding.projectcapstone;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class KategoriActivity {
    public class kategori_activity extends AppCompatActivity {

        private RecyclerView foodList;
        private ArrayList<Kategori> kategoriList;
        private KategoriAdapter kategoriAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.item_kategori);

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
