package com.inquirypro.ui.auth.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.inquirypro.R
import com.inquirypro.base.BaseFragment
import com.inquirypro.databinding.FragmentAuthBinding
import com.inquirypro.util.NavigationUtils

class AuthFragment : BaseFragment(R.layout.fragment_auth) {

    private lateinit var binding: FragmentAuthBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthBinding.bind(view)
        setupListener()
    }

    private fun setupListener() {

        binding.btnSignIn.setOnClickListener {
            findNavController().navigate(
                R.id.action_authFragment_to_signInFragment, null, NavigationUtils.slideAnimLeft())
        }

        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(
                R.id.action_authFragment_to_signUpFragment,null, NavigationUtils.slideAnimLeft())
        }
    }
}