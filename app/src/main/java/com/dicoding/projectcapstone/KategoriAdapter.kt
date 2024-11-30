package com.dicoding.projectcapstone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class KategoriAdapter(private val kategoriList: ArrayList<Kategori>) :
    RecyclerView.Adapter<KategoriAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_kategori, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kategori = kategoriList[position]
        holder.imageView.setImageResource(kategori.imageResource)
        holder.textView.text = kategori.name
    }

    override fun getItemCount(): Int {
        return kategoriList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView =
            itemView.findViewById(R.id.item_image)
        var textView: TextView =
            itemView.findViewById(R.id.item_name)
    }
}

