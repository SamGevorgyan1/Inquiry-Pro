package com.inquirypro.ui.auth.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.util.Log
import android.util.Patterns
import androidx.navigation.fragment.findNavController
import com.inquirypro.R
import com.inquirypro.base.BaseAuthFragment
import com.inquirypro.databinding.FragmentForgotPasswordBinding
import com.inquirypro.repository.Result
import org.koin.androidx.viewmodel.ext.android.viewModel

import com.inquirypro.ui.auth.viewmodel.ResetPasswordViewModel
import com.inquirypro.util.NavigationUtils

class ForgotPasswordFragment : BaseAuthFragment(R.layout.fragment_forgot_password) {

    private lateinit var binding: FragmentForgotPasswordBinding
    private val viewModel by viewModel<ResetPasswordViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentForgotPasswordBinding.bind(view)

        setupConfirmButton()
    }

    private fun setupConfirmButton() {
        binding.btnConfirm.setOnClickListener {
            val email = binding.etEmail.text.toString()

            if (email.isEmpty()) {
                showInputError("Email is required")
            } else if (!isValidEmail(email)) {
                showInputError("Invalid email address")
            } else {
                clearInputError()
                resetPassword(email)
            }
        }
    }

    private fun showInputError(error: String) {
        binding.etEmail.error = error
    }

    private fun clearInputError() {
        binding.etEmail.error = null
    }

    private fun resetPassword(email: String) {
        viewModel.resetPassword(email)
        viewModel.confirmResponse.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    val response = result.data
                    val bundle = Bundle().apply { putString("email", email) }

                    navigateToResetPasswordScreen(bundle)
                    showToast(response.message)
                }

                is Result.Error -> {
                    showToast(result.message)
                    Log.e("resetPassword", result.message)
                }
            }
        }
    }

    private fun navigateToResetPasswordScreen(bundle: Bundle) {
        findNavController().navigate(
            R.id.action_forgotPasswordFragment_to_resetPasswordFragment,
            bundle,
            NavigationUtils.slideAnimLeft()
        )
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = Patterns.EMAIL_ADDRESS
        return email.matches(emailPattern.toRegex())
    }

    override fun handleOnBackPressed() {
        findNavController().popBackStack()
    }
}
