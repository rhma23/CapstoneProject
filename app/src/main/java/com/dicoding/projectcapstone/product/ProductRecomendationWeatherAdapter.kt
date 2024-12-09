package com.dicoding.projectcapstone.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.projectcapstone.R
import com.dicoding.projectcapstone.utils.Helper

class ProductRecomendationWeatherAdapter(
    private var events: List<DataItem>,
    private val onItemClick: (DataItem) -> Unit
) : RecyclerView.Adapter<ProductRecomendationWeatherAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var helper: Helper = Helper()
        val productImage: ImageView = itemView.findViewById(R.id.item_image)
        val productName: TextView = itemView.findViewById(R.id.item_name)
        val productPrice: TextView = itemView.findViewById(R.id.item_price)
        val productRating: TextView = itemView.findViewById(R.id.item_rating)
        val productDesc: TextView = itemView.findViewById(R.id.item_description)
        val productStatus: TextView = itemView.findViewById(R.id.item_status)

        fun bind(event: DataItem, onItemClick: (DataItem) -> Unit) {
            productName.text = event.name ?: "Unnamed Event"
            productPrice.text = helper.formatRupiah(event.price?.toInt() ?: 0)
            productRating.text = (event.merchant?.average_rating ?: "Unknown Rating").toString()
            productStatus.text = event.merchant?.status ?: "Unknown Status"
            productDesc.text = event.description ?: "Unknown Description"
            if (event.merchant?.status == "tutup") {
                productStatus.setTextColor(itemView.context.resources.getColor(R.color.red))
            } else {
                productStatus.setTextColor(itemView.context.resources.getColor(R.color.green))
            }
            Glide.with(itemView.context)
                .load(event.image?.let { helper.removePath(it) })
                .into(productImage)
            itemView.setOnClickListener { onItemClick(event) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kategori, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(events[position], onItemClick)
    }

    override fun getItemCount(): Int = events.size

    fun updateData(newEvents: List<DataItem>) {
        events = newEvents
        notifyDataSetChanged()
    }
}