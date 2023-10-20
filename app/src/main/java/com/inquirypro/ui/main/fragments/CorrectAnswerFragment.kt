package com.inquirypro.ui.main.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.inquirypro.R
import com.inquirypro.databinding.FragmentCorrectAnswerBinding
import com.inquirypro.model.login.LoginResponse
import com.inquirypro.ui.main.adapters.AnswerAdapter
import com.inquirypro.ui.viewmodel.QuestionResultViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CorrectAnswerFragment : Fragment(R.layout.fragment_correct_answer) {

    private lateinit var binding: FragmentCorrectAnswerBinding
    private val questionResultViewModel by viewModel<QuestionResultViewModel>()
    private val correctAnswerAdapter = AnswerAdapter(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = LoginResponse.retrieverUser()

        user?.id?.let { userId ->
            questionResultViewModel.getUserCorrectAnswers(userId)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCorrectAnswerBinding.bind(view)

        questionResultViewModel.userCorrectAnswers.observe(viewLifecycleOwner) { userCorrectAnswers ->
            userCorrectAnswers?.let { correctAnswerAdapter.updateData(it) }
        }
        setupView()
    }

    private fun setupView() {
        binding.rvUserCorrectAnswer.adapter = correctAnswerAdapter
    }
}
