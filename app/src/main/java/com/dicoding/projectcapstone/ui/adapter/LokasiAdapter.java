package com.dicoding.projectcapstone.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.dicoding.projectcapstone.R;
import java.util.ArrayList;
import com.dicoding.projectcapstone.model.Lokasi;


public class LokasiAdapter extends RecyclerView.Adapter<LokasiAdapter.LokasiViewHolder> {

    private ArrayList<Lokasi> lokasiList;

    public LokasiAdapter(ArrayList<Lokasi> lokasiList) {
        this.lokasiList = lokasiList;
    }

    @NonNull
    @Override
    public LokasiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lokasi, parent, false);
        return new LokasiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LokasiViewHolder holder, int position) {
        Lokasi lokasi = lokasiList.get(position);
        holder.nameTextView.setText(lokasi.getName());
        holder.distanceTextView.setText(lokasi.getDistance());
        holder.timeTextView.setText(lokasi.getTime());
        holder.imageView.setImageResource(lokasi.getImageResource());
        holder.ratingBar.setRating((float) lokasi.getRating());
    }

    @Override
    public int getItemCount() {
        return lokasiList.size();
    }

    public static class LokasiViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, distanceTextView, timeTextView;
        ImageView imageView;
        RatingBar ratingBar;

        public LokasiViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_name);
            distanceTextView = itemView.findViewById(R.id.text_distance);
            timeTextView = itemView.findViewById(R.id.text_time);
            imageView = itemView.findViewById(R.id.image_location);
            ratingBar = itemView.findViewById(R.id.rating_location);
        }
    }
}