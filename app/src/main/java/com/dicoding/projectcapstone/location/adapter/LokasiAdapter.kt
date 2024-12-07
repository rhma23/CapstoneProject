package com.dicoding.projectcapstone.location.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.projectcapstone.R
import com.dicoding.projectcapstone.location.model.Lokasi
import org.w3c.dom.Text

class LokasiAdapter(private val lokasiList: ArrayList<Lokasi>) :
    RecyclerView.Adapter<LokasiAdapter.LokasiViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LokasiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lokasi, parent, false)
        return LokasiViewHolder(view)
    }

    override fun onBindViewHolder(holder: LokasiViewHolder, position: Int) {
        val lokasi = lokasiList[position]
        holder.nameTextView.text = lokasi.name
        holder.imageView.setImageResource(lokasi.imageResource)
        holder.ratingBar.rating = lokasi.rating.toFloat()
        holder.ratingText.text = lokasi.rating.toString()
        holder.distanceView.text = String.format("%.2f km", lokasi.distance)
        if (lokasi.isOpen) {
            holder.itemStatus.text = "Buka"
            holder.itemStatus.setTextColor(0xFF4CAF50.toInt())
        } else {
            holder.itemStatus.text = "Tutup"
            holder.itemStatus.setTextColor(0xFFF44336.toInt())
        }
    }

    override fun getItemCount(): Int {
        return lokasiList.size
    }

    class LokasiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameTextView: TextView =
            itemView.findViewById(R.id.text_name)
        var imageView: ImageView =
            itemView.findViewById(R.id.image_location)
        var distanceView: TextView =
            itemView.findViewById(R.id.item_distance)
        var ratingBar: RatingBar = itemView.findViewById(R.id.rating_location)
        var ratingText: TextView =
            itemView.findViewById(R.id.text_rating_value)
        var itemStatus: TextView =
            itemView.findViewById(R.id.item_status)
    }

    fun addLokasi(lokasi: Lokasi) {
        lokasiList.add(lokasi)
    }
}