package com.dicoding.projectcapstone.product

import com.google.gson.annotations.SerializedName

data class GetAllProductResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("merchant")
	val merchant: Merchant? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("merchant_id")
	val merchantId: Int? = null,

	@field:SerializedName("category")
	val category: List<CategoryItem?>? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class CategoryItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("Product_Category_Mapping")
	val productCategoryMapping: ProductCategoryMapping? = null
)

data class ProductCategoryMapping(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("category_id")
	val categoryId: Int? = null,

	@field:SerializedName("product_id")
	val productId: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class Merchant(

	@field:SerializedName("business_name")
	val businessName: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("average_rating")
	val averageRating: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)
