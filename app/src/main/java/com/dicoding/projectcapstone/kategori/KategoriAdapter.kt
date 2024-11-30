package com.dicoding.projectcapstone.kategori;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.dicoding.projectcapstone.R;
import java.util.ArrayList;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.ViewHolder> {

    private final ArrayList<Kategori> kategoriList;

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

        holder.itemImage.setImageResource(kategori.getImageResource());
        holder.itemName.setText(kategori.getName());
        holder.itemRating.setText(String.valueOf(kategori.getRating()));
        holder.itemDistance.setText(kategori.getDistance());
        holder.itemPrice.setText(kategori.getPrice());
        holder.itemStatus.setText(kategori.isOpen() ? "Buka" : "Tutup");
    }

    @Override
    public int getItemCount() {
        return kategoriList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView itemImage;
        public TextView itemName, itemRating, itemDistance, itemPrice, itemStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemName = itemView.findViewById(R.id.item_name);
            itemRating = itemView.findViewById(R.id.item_rating);
            itemDistance = itemView.findViewById(R.id.item_distance);
            itemPrice = itemView.findViewById(R.id.item_price);
            itemStatus = itemView.findViewById(R.id.item_status);
        }
    }
}
