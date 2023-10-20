package com.inquirypro.ui.auth.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.inquirypro.R
import com.inquirypro.base.BaseAuthFragment
import com.inquirypro.databinding.FragmentSignInBinding
import com.inquirypro.model.login.LoginRequest
import com.inquirypro.model.login.LoginResponse
import com.inquirypro.repository.Result
import com.inquirypro.ui.auth.viewmodel.LoginViewModel
import com.inquirypro.ui.main.MainActivity
import com.inquirypro.util.NavigationUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : BaseAuthFragment(R.layout.fragment_sign_in) {

    private lateinit var binding: FragmentSignInBinding
    private val viewModel by viewModel<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignInBinding.bind(view)

        setupGoogleSignInButton()
        setupSignInButton()
        setupFacebookButton()
        setupForgotPasswordButton()
    }

    private fun setupGoogleSignInButton() {
        binding.btnGoogle.setOnClickListener {
            signInIntent()
            val (signInUser, errorMessage) = getSignInUser()

            if (signInUser != null) {
                if (signInUser.email != null && signInUser.accountId != null) {
                   viewModel.login(LoginRequest(signInUser.email,signInUser.accountId),true)

                    viewModel.loginResponse.observe(viewLifecycleOwner){result->
                        when (result) {
                            is Result.Success -> {
                                handleLoginSuccess(result.data)
                            }
                            is Result.Error -> {
                                showErrorMessage(result.message)
                            }
                        }
                    }
                } else {
                    showInputError("Please enter both email and password")
                }
            }
        }
    }

    private fun setupSignInButton() {
        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                performLogin(email, password)
            } else {
                showInputError("Please enter both email and password")
            }
        }
    }

    private fun setupFacebookButton() {
        binding.btnFaceBook.setOnClickListener {
            val retrieveLoginResponse = LoginResponse.retrieveLoginResponse()
            Log.i("loginResponseSingle", retrieveLoginResponse.toString())
        }
    }

    private fun setupForgotPasswordButton() {
        binding.btnForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_forgotPasswordFragment)
        }
    }

    private fun performLogin(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)

        viewModel.login(loginRequest, false)

        viewModel.loginResponse.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    handleLoginSuccess(result.data)
                }
                is Result.Error -> {
                    showErrorMessage(result.message)
                }
            }
        }
    }

    private fun handleLoginSuccess(loginResponse: LoginResponse) {
        Log.i("loginResponse", loginResponse.user.toString())
        Log.i("loginResponse", loginResponse.token.toString())
        Log.i("loginResponse", loginResponse.message.toString())

        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun showInputError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun handleOnBackPressed() {
        findNavController().navigate(
            R.id.action_signInFragment_to_authFragment,
            null,
            NavigationUtils.slideAnimRight()
        )
    }
}