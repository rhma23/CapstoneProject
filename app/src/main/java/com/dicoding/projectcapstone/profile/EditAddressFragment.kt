package com.dicoding.projectcapstone.profile

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicoding.projectcapstone.databinding.FragmentEditAddressBinding

class EditAddressFragment : Fragment() {
    private var _binding: FragmentEditAddressBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = requireActivity().getSharedPreferences("UserPrefs", AppCompatActivity.MODE_PRIVATE)

        // Ambil data dari SharedPreferences
        val street = sharedPref.getString("street", "")
        val city = sharedPref.getString("city", "")
        val postalCode = sharedPref.getString("postal_code", "")

        // Tampilkan data di EditText
        binding.editStreetName.setText(street)
        binding.editCity.setText(city)
        binding.editPostalCode.setText(postalCode)

        // Simpan data saat tombol "Save" diklik
        binding.saveAddressButton.setOnClickListener {
            val newStreet = binding.editStreetName.text.toString()
            val newCity = binding.editCity.text.toString()
            val newPostalCode = binding.editPostalCode.text.toString()

            // Validasi input
            if (newStreet.isEmpty() || newCity.isEmpty() || newPostalCode.isEmpty()) {
                Toast.makeText(requireContext(), "Harap isi semua bidang", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Simpan ke SharedPreferences
            with(sharedPref.edit()) {
                putString("street", newStreet)
                putString("city", newCity)
                putString("postal_code", newPostalCode)
                putString("address", "$newStreet, $newCity, $newPostalCode")
                apply()
            }

            Toast.makeText(requireContext(), "Alamat berhasil disimpan", Toast.LENGTH_SHORT).show()
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
