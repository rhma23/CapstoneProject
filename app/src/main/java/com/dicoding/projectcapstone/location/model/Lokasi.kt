package com.dicoding.projectcapstone.location.model

class Lokasi(
    @JvmField val name: String,
    val imageUrl: String,
    val rating: Double,
    val isOpen: Boolean,
    val distance: Double,
    val latitude: Double,
    val longitude: Double
)