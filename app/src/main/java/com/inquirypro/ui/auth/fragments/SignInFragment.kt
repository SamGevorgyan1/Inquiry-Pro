package com.inquirypro.ui.auth.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.inquirypro.R
import com.inquirypro.databinding.FragmentSignInBinding
import com.inquirypro.model.User
import com.inquirypro.ui.main.MainActivity
import com.inquirypro.ui.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private lateinit var binding: FragmentSignInBinding
    private val viewModel by viewModel<UserViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignInBinding.bind(view)

        viewModel.loginResult.observe(this) { result ->
            if (result != null) {
                val intent = Intent(requireContext(), MainActivity::class.java)
                activity?.startActivity(intent)
                Log.i("Login result", "Login successful: ${result.name}")
            } else {

                Log.i("Login result", "Login failed")
            }
        }

        viewModel.loginUser(Us.user)

    }
}

object Us {
    val user = User(name = "Samvel", email = "samg05981@gmail.com", password = "123456789", id = 1)
}