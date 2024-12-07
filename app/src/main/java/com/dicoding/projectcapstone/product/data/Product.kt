package com.dicoding.projectcapstone.product.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class ProductEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val price: String,
    val image: String?,
    val description: String?,
    val updatedAt: String
)

