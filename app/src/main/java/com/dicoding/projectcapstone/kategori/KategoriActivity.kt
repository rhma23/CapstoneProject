package com.dicoding.projectcapstone.kategori;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dicoding.projectcapstone.R;
import java.util.ArrayList;

public class KategoriActivity extends AppCompatActivity {

    private RecyclerView foodList;
    private RecyclerView drinkList;
    private ArrayList<Kategori> kategoriList;
    private KategoriAdapter kategoriAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        // Inisialisasi RecyclerView
        foodList = findViewById(R.id.food_list);
        drinkList = findViewById(R.id.drink_list);

        // Membuat data kategori
        kategoriList = new ArrayList<>();
        kategoriList.add(new Kategori(R.drawable.ic_food, "Food", 4.5, "2 km", "$10", true));
        kategoriList.add(new Kategori(R.drawable.ic_drink, "Drink", 4.0, "1.5 km", "$5", false));


        // Inisialisasi Adapter
        kategoriAdapter = new KategoriAdapter(kategoriList);

        // Atur RecyclerView untuk daftar makanan
        foodList.setAdapter(kategoriAdapter);
        foodList.setLayoutManager(new LinearLayoutManager(this));

        // Atur RecyclerView untuk daftar minuman
        drinkList.setAdapter(kategoriAdapter);
        drinkList.setLayoutManager(new LinearLayoutManager(this));
    }
}
