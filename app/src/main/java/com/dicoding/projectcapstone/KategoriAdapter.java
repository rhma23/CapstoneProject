package com.dicoding.projectcapstone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class KategoriAdapter extends RecyclerView.Adapter<com.dicoding.projectcapstone.KategoriAdapter.ViewHolder> {

        private ArrayList<Kategori> kategoriList;

        public KategoriAdapter(ArrayList<Kategori> kategoriList) {
            this.kategoriList = kategoriList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_kategori, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Kategori kategori = kategoriList.get(position);
            holder.imageView.setImageResource(kategori.getImageResource());
            holder.textView.setText(kategori.getName());
        }

        @Override
        public int getItemCount() {
            return kategoriList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public ImageView imageView;
            public TextView textView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.item_image);
                textView = itemView.findViewById(R.id.item_name);
            }
        }
    }

