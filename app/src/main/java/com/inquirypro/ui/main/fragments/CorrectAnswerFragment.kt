package com.inquirypro.ui.main.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.inquirypro.R
import com.inquirypro.databinding.FragmentCorrectAnswerBinding
import com.inquirypro.ui.auth.fragments.Us
import com.inquirypro.ui.main.adapters.UserCorrectAnswersAdapter
import com.inquirypro.ui.viewmodel.QuestionStoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CorrectAnswerFragment : Fragment(R.layout.fragment_correct_answer) {

    private lateinit var binding: FragmentCorrectAnswerBinding
    private val questionStoryViewModel by viewModel<QuestionStoryViewModel>()
    private val correctAnswersAdapter = UserCorrectAnswersAdapter(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Us.user.id?.let { questionStoryViewModel.getUserCorrectAnswers(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCorrectAnswerBinding.bind(view)

        questionStoryViewModel.userCorrectAnswers.observe(viewLifecycleOwner) {
            it?.let { correctAnswersAdapter.updateData(it) }
        }
        setupView()
    }

    private fun setupView() {
        binding.rvUserCorrectAnswer.adapter = correctAnswersAdapter
    }
}