package com.inquirypro.ui.auth.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.inquirypro.R
import com.inquirypro.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding: FragmentSignUpBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)
    }
}