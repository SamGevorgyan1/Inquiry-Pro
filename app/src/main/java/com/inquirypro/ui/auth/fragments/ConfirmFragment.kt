package com.inquirypro.ui.auth.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.inquirypro.R
import com.inquirypro.base.BaseFragment
import com.inquirypro.databinding.FragmentConfirmBinding
import com.inquirypro.model.login.LoginRequest
import com.inquirypro.repository.Result
import com.inquirypro.ui.auth.viewmodel.LoginViewModel
import com.inquirypro.ui.auth.viewmodel.RegistrationViewModel
import com.inquirypro.ui.main.MainActivity
import com.inquirypro.util.Constants.Companion.EMAIL
import com.inquirypro.util.Constants.Companion.PASSWORD
import com.inquirypro.util.Constants.Companion.TOKEN
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConfirmFragment : BaseFragment(R.layout.fragment_confirm) {


    private lateinit var binding: FragmentConfirmBinding
    private val viewModel by viewModel<RegistrationViewModel>()
    private val loginViewModel by viewModel<LoginViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentConfirmBinding.bind(view)

        setupClickListener()
    }

    private fun setupClickListener() {

        binding.btnConfirm.setOnClickListener {
            perform()
        }
    }

    private fun perform() {
        val token = arguments?.getString(TOKEN)
        val email = arguments?.getString(EMAIL)
        val password = arguments?.getString(PASSWORD)

        val confirmCode = binding.etConfirmCode.text.toString()


        if (token != null) {
            viewModel.confirmCode(token, confirmCode)
        }

        viewModel.confirmResponse.observe(viewLifecycleOwner) { result ->
            Log.i("token token", token.toString())
            Log.i("email", token.toString())
            Log.i("password", token.toString())



            when (result) {
                is Result.Success -> {
                    Log.i("ConfirmationResponse", result.data.message)

                    if (email != null && password != null) {
                        loginViewModel.login(LoginRequest(email, password),false)
                    }
                    loginViewModel.loginResponse.observe(viewLifecycleOwner) {
                        if (it is Result.Success) {
                            val intent = Intent(requireContext(), MainActivity::class.java)
                            activity?.startActivity(intent)
                        }
                    }
                }
                is Result.Error -> {
                    Log.i("ConfirmationResponse", result.message)
                }
            }
        }
    }
}