package com.dicoding.projectcapstone.product

data class ProductDetail(
    val id: Int,
    val name: String,
    val rate: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val seller: String
)