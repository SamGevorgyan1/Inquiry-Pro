package com.inquirypro.ui.auth.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.inquirypro.R
import com.inquirypro.databinding.FragmentAuthBinding

class AuthFragment : Fragment(R.layout.fragment_auth) {

    private lateinit var binding: FragmentAuthBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthBinding.bind(view)


        setupListener()
    }

    private fun setupListener() {
        binding.btnSignIn.setOnClickListener { findNavController().navigate(R.id.action_authFragment_to_signInFragment) }

        binding.btnSignUp.setOnClickListener { findNavController().navigate(R.id.action_authFragment_to_signUpFragment) }
    }
}