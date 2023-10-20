package com.inquirypro.ui.auth.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.inquirypro.R
import com.inquirypro.base.BaseAuthFragment
import com.inquirypro.databinding.FragmentSignUpBinding
import com.inquirypro.model.registration.RegistrationRequest
import com.inquirypro.model.registration.RegistrationRequestGoogle
import com.inquirypro.repository.Result
import com.inquirypro.ui.auth.viewmodel.RegistrationViewModel
import com.inquirypro.ui.main.MainActivity
import com.inquirypro.util.Constants.Companion.EMAIL
import com.inquirypro.util.Constants.Companion.PASSWORD
import com.inquirypro.util.Constants.Companion.TOKEN
import com.inquirypro.util.NavigationUtils
import com.inquirypro.util.helper.UserValidation
import org.koin.androidx.viewmodel.ext.android.viewModel


class SignUpFragment : BaseAuthFragment(R.layout.fragment_sign_up) {


    private lateinit var binding: FragmentSignUpBinding
    private val viewModel by viewModel<RegistrationViewModel>()
    

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)


        binding.btnGoogle.setOnClickListener {
            signInIntent()
            val (user, errorMessage) = getSignInUser()



            if (user != null) {
                Log.i("user account id", user.accountId.toString())
                Log.i("user first name ", user.firstName.toString())
                Log.i("user last name", user.lastName.toString())
                Log.i("user email", user.email.toString())

                if (user.firstName != null && user.lastName != null && user.email != null && user.accountId != null) {
                    viewModel.registerWithGoogle(
                        RegistrationRequestGoogle(
                            user.firstName,
                            user.lastName,
                            user.email,
                            user.accountId
                        )
                    )

                    viewModel.registerResponse.observe(viewLifecycleOwner){result->
                        when(result){
                            is Result.Success ->{

                                val intent = Intent(requireContext(),MainActivity::class.java)
                                activity?.startActivity(intent)
                            }
                            is Result.Error->{
                                Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }

        binding.btnSignUp.setOnClickListener {
            if (validateInput()) {
                performSignUp()
            }
        }
    }


    private fun validateInput(): Boolean {
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        try {
            UserValidation.validateUserInput(firstName, lastName, email, password)
            return true
        } catch (e: IllegalArgumentException) {

            when (e.message) {
                UserValidation.INVALID_FIRST_NAME -> {
                    binding.etFirstName.error = e.message
                    binding.etFirstName.requestFocus()
                }

                UserValidation.INVALID_LAST_NAME -> {
                    binding.etLastName.error = e.message
                    binding.etLastName.requestFocus()
                }

                UserValidation.INVALID_EMAIL -> {
                    binding.etEmail.error = e.message
                    binding.etEmail.requestFocus()
                }

                UserValidation.INVALID_PASSWORD -> {
                    binding.etPassword.error = e.message
                    binding.etPassword.requestFocus()
                }
            }
            return false
        }
    }

    private fun performSignUp() {

        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()


        val loginRequest = RegistrationRequest(firstName, lastName, email, password)

        viewModel.register(loginRequest)

        viewModel.registerResponse.observe(viewLifecycleOwner) { result ->

            when (result) {
                is Result.Success -> {
                    val registerResponse = result.data
                    Log.i("registrationResponse", registerResponse.user.toString())
                    Log.i("registrationResponse", registerResponse.token)
                    Log.i("registrationResponse", registerResponse.message)

                    val bundle = Bundle().apply {
                        putString(TOKEN, registerResponse.token)
                        putString(EMAIL, registerResponse.user.email)
                        putString(PASSWORD, password)
                    }

                    findNavController().navigate(
                        R.id.action_signUpFragment_to_confirmFragment,
                        bundle
                    )
                }

                is Result.Error -> Log.i("registrationResponse", result.message)
            }
        }
    }

    override fun handleOnBackPressed() {
        findNavController().popBackStack()
    }
}