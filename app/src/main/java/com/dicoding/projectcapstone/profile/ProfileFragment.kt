package com.dicoding.projectcapstone.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.projectcapstone.R

class ProfileFragment : Fragment() {

    // Views
    private lateinit var showName: EditText
    private lateinit var showEmail: EditText
    private lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment layout
        return inflater.inflate(R.layout.fragment_show_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        showName = view.findViewById(R.id.show_full_name)
        showEmail = view.findViewById(R.id.show_email)
        toolbar = requireActivity().findViewById(R.id.toolbar)

        // Set toolbar title and color
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setTitle("Show Profile")
        toolbar.setBackgroundColor(resources.getColor(R.color.blue))
        toolbar.setTitleTextColor(resources.getColor(android.R.color.white))

    }
}
