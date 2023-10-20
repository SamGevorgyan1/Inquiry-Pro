package com.inquirypro.ui.main.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.inquirypro.R
import com.inquirypro.databinding.FragmentIncorrectAnswerBinding
import com.inquirypro.model.login.LoginResponse

import com.inquirypro.ui.main.adapters.AnswerAdapter
import com.inquirypro.ui.viewmodel.QuestionResultViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class InCorrectAnswerFragment : Fragment(R.layout.fragment_incorrect_answer) {

    private lateinit var binding: FragmentIncorrectAnswerBinding
    private val questionResultViewModel by viewModel<QuestionResultViewModel>()
    private val answerAdapter = AnswerAdapter(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = LoginResponse.retrieverUser()

        user?.id?.let { questionResultViewModel.getUserIncorrectAnswers(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentIncorrectAnswerBinding.bind(view)

        questionResultViewModel.userIncorrectAnswers.observe(viewLifecycleOwner) {
            it?.let { answerAdapter.updateData(it) }
            setupView()
        }
    }

    private fun setupView() {
        binding.rvIncorrectAnswer.adapter = answerAdapter
    }
}