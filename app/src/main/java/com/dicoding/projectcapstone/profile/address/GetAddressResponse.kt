import com.google.gson.annotations.SerializedName

data class GetAddressResponse(
	val id: Int,
	val user_id: Int,
	val address_name: String,
	val latitude: Double,
	val longitude: Double,
	val createdAt: String,
	val updatedAt: String,
	val User: User
)

data class User(
	val id: Int,
	val username: String,
	val email: String,
	val role: String
)

