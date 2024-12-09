package com.dicoding.projectcapstone.kategori.adapter

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

class CategoryAdapter(
    private val productList: List<DataItem>,
    private val onItemClick: (DataItem) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.MakananViewHolder>() {

    inner class MakananViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.item_name)
        val price: TextView = itemView.findViewById(R.id.item_price)
        val image: ImageView = itemView.findViewById(R.id.item_image)
        val description: TextView = itemView.findViewById(R.id.item_description)

        // Bind data to views and set up the onClick listener
        fun bind(product: DataItem) {
            name.text = product.name
            description.text = product.description
            price.text = product.price?.toString() ?: "N/A"

            // Construct the full image URL
            val fullImageUrl = "${RetrofitClient.getBaseIp()}/images/products/${product.image}"
            Glide.with(itemView.context)
                .load(fullImageUrl)
                .into(image)

            // Set the onClick listener to call the onItemClick function
            itemView.setOnClickListener {
                onItemClick(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MakananViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kategori, parent, false)
        return MakananViewHolder(view)
    }

    override fun onBindViewHolder(holder: MakananViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product) // Bind the product to the ViewHolder
    }

    override fun getItemCount(): Int = productList.size
}

