package com.dicoding.projectcapstone.banner

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dicoding.projectcapstone.api.RetrofitClient.apiService
import com.dicoding.projectcapstone.databinding.FragmentScrollingRecProductBinding
import com.dicoding.projectcapstone.product.DetailProductActivity
import com.dicoding.projectcapstone.product.ProductModel
import com.dicoding.projectcapstone.product.ProductRecomendationWeatherAdapter
import com.dicoding.projectcapstone.product.ProductRepository
import com.dicoding.projectcapstone.product.ProductViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ScrollingFragmentRecProduct : BottomSheetDialogFragment() {

    private var _binding: FragmentScrollingRecProductBinding? = null
    private val binding get() = _binding!!

    private val productViewModel: ProductModel by viewModels {
        ProductViewModelFactory(ProductRepository(apiService))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScrollingRecProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        productViewModel.fetchAllProductsRecommendationsFromMl()

        val productAdapter = ProductRecomendationWeatherAdapter(
            events = listOf(),
            onItemClick = { dataItem ->
                Toast.makeText(requireContext(), "Clicked on ${dataItem.name}", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), DetailProductActivity::class.java)
                intent.putExtra("id", dataItem.id)
                startActivity(intent)
            }
        )

        val layoutManager = StaggeredGridLayoutManager(
            1, // Number of columns
            StaggeredGridLayoutManager.VERTICAL // Vertical orientation
        )

        binding.rvRecProduct.apply {
            this.layoutManager = layoutManager
            adapter = productAdapter
            setHasFixedSize(true)
        }

        productViewModel.productsRecommendationsFromMl.observe(viewLifecycleOwner) { productList ->
            productList?.let {
                Log.d("ScrolingFragmentRecProduct", "${it}")
                productAdapter.updateData(it)
            } ?: run {
                Log.d("ScrolingFragmentRecProduct", "Product list is null or empty")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun removePath(imagePath: String): String {
        // Implement the logic to remove the path from the image URL
        return imagePath
    }
}