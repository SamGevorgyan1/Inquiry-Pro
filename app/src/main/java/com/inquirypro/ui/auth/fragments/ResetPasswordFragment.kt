package com.inquirypro.ui.auth.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.inquirypro.R
import com.inquirypro.base.BaseAuthFragment
import com.inquirypro.databinding.FragmentResetPasswordBinding
import com.inquirypro.model.login.LoginRequest
import com.inquirypro.repository.Result
import com.inquirypro.ui.auth.viewmodel.LoginViewModel
import com.inquirypro.ui.auth.viewmodel.ResetPasswordViewModel
import com.inquirypro.ui.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResetPasswordFragment : BaseAuthFragment(R.layout.fragment_reset_password) {

    private lateinit var binding: FragmentResetPasswordBinding
    private val viewModel by viewModel<ResetPasswordViewModel>()
    private val loginViewModel by viewModel<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentResetPasswordBinding.bind(view)

        val email = arguments?.getString("email")

        binding.btnConfirm.setOnClickListener {
            val resetToken = binding.etResetToken.text.toString()
            val password = binding.etPassword.text.toString()
            val repeatPassword = binding.etRepeatPassword.text.toString()

            if (resetToken.isNotEmpty() && email != null && password.isNotEmpty()) {
                if (password == repeatPassword) {
                    clearInputErrors()
                    resetPassword(email, password, resetToken)
                } else {
                    showErrorInEditText(binding.etRepeatPassword, "Passwords do not match")
                }
            } else {
                showInputError("Please fill in all fields")
            }
        }
    }

    private fun resetPassword(email: String, password: String, resetToken: String) {
        viewModel.setNewPassword(password, email, resetToken)
        viewModel.resetPasswordResponse.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    showToast("Reset password success")
                    login(email, password)
                }
                is Result.Error -> {
                    showToast("Reset password failed: ${result.message}")
                }
            }
        }
    }

    private fun login(email: String, password: String) {
        loginViewModel.login(LoginRequest(email, password), false)
        loginViewModel.loginResponse.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    startMainActivity()
                }
                is Result.Error -> {
                    showToast(result.message)
                }
            }
        }
    }

    private fun startMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        activity?.startActivity(intent)
    }

    private fun showInputError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

    private fun clearInputErrors() {
        binding.etPassword.error = null
        binding.etRepeatPassword.error = null
    }

    private fun showErrorInEditText(editText: EditText, error: String) {
        editText.error = error
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
