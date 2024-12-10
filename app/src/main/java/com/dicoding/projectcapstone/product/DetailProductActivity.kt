package com.dicoding.projectcapstone.product

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.projectcapstone.R
import com.dicoding.projectcapstone.api.RetrofitClient.apiService
import com.dicoding.projectcapstone.location.LokasiActivity
import com.dicoding.projectcapstone.ui.MyButton
import com.dicoding.projectcapstone.utils.Helper

class DetailProductActivity : AppCompatActivity() {
    private var helper: Helper = Helper()
    private val productViewModel: ProductModel by viewModels {
        ProductViewModelFactory(ProductRepository(apiService))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)

        val lyDetailProductTop: LinearLayout = findViewById(R.id.product_detail_container)
        val progressBar: ProgressBar = findViewById(R.id.loading_spinner)
        val txtLoadingMessage: TextView = findViewById(R.id.loading_text)
        val addToCartButton: Button = findViewById(R.id.add_to_cart_button)

        //membuat indikator loading
        lyDetailProductTop.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
        txtLoadingMessage.visibility = View.VISIBLE

        // Menerima data dari Intent
        val id = intent.getIntExtra("id", -1)
        if (id != -1) {
            Log.d(TAG, "EventId: $id")
            productViewModel.fetchProductDetail(id)
        } else {
            Log.e(TAG, "Invalid event ID")
        }

        // Menampilkan data ke dalam view
        val nameTextView: TextView = findViewById(R.id.product_name)
        val rateTextView: TextView = findViewById(R.id.product_rating)
        val descriptionTextView: TextView = findViewById(R.id.product_description)
        val priceTextView: TextView = findViewById(R.id.product_price)
        val sellerTextView: TextView = findViewById(R.id.merchant_name)
        val imageView: ImageView = findViewById(R.id.product_image)

        Handler(Looper.getMainLooper()).postDelayed({
            productViewModel.productDetailLiveData.observe(this) { productDetail ->
                productDetail?.data?.let { data ->
                    nameTextView.text = data.name ?: "Unnamed Product"
                    rateTextView.text = (data.merchant?.average_rating ?: "Unknown Rate").toString()
                    descriptionTextView.text = data.description ?: "Unknown Description"
                    priceTextView.text = data.price?.let { helper.formatRupiah(it.toInt()) }
                    sellerTextView.text = data.merchant?.business_name ?: "Unknown Seller"
                    Log.d("DetailProductActivity", "onCreate: ${data.image}")

                    Glide.with(this)
                        .load(data.image?.let { helper.removePath(it)})
                        .into(imageView)

                    //membuat indikator loading
                    lyDetailProductTop.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    txtLoadingMessage.visibility = View.GONE
                } ?: run {
                    Log.e(TAG, "Product detail is null")
                }
            }
        }, 3000)

        // Set listener for the "Add to Cart" button
//        val addToCartButton: MyButton = findViewById(R.id.add_to_cart_button)
//        addTgit oCartButton.setOnClickListener {
//            // Action when button is clicked
//            // This can be redirecting to another activity or showing a toast, etc.
//            Log.d("DetailProduct", "Add to Cart button clicked")
//        }

        //move to seller
        addToCartButton.setOnClickListener {
            val intent = Intent(this, LokasiActivity::class.java)
            val merchantName = productViewModel.productDetailLiveData.value?.data?.merchant?.business_name
            if (merchantName != null) {
                intent.putExtra("merchant_name", merchantName)
                Log.i(TAG, "onCreate: "+merchantName)
                startActivity(intent)
            } else {
                Log.e("DetailProductActivity", "Merchant name is null")
            }
        }
    }
}