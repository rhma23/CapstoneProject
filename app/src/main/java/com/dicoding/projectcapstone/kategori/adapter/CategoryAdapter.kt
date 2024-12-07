package com.dicoding.projectcapstone.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.projectcapstone.R
import com.dicoding.projectcapstone.api.RetrofitClient
import com.dicoding.projectcapstone.product.DataItem

class CategoryAdapter(private val productList: List<DataItem>) :
    RecyclerView.Adapter<CategoryAdapter.MakananViewHolder>() {
    inner class MakananViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.item_name)
        val price: TextView = itemView.findViewById(R.id.item_price)
        val image: ImageView = itemView.findViewById(R.id.item_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MakananViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kategori, parent, false)
        return MakananViewHolder(view)
    }

    override fun onBindViewHolder(holder: MakananViewHolder, position: Int) {
        val product = productList[position]
        val fullImageUrl = RetrofitClient.getBaseIp() + "/images/products/" + product.image
        holder.name.text = product.name
        holder.price.text = product.price

        Glide.with(holder.itemView.context).load(fullImageUrl).into(holder.image)
    }

    override fun getItemCount(): Int = productList.size
}

