package com.dicoding.projectcapstone.banner.weather

import com.google.gson.annotations.SerializedName

data class WeatherResponse(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("address_id")
	val address_id: Int? = null,

	@field:SerializedName("temperature")
	val temperature: Any? = null,

	@field:SerializedName("weather_desc")
	val weather_desc: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("weather_main")
	val weather_main: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
