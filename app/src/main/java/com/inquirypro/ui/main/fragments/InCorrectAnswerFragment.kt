package com.inquirypro.ui.main.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.inquirypro.R
import com.inquirypro.databinding.FragmentIncorrectAnswerBinding
import com.inquirypro.ui.auth.fragments.Us
import com.inquirypro.ui.main.adapters.UserCorrectAnswersAdapter
import com.inquirypro.ui.viewmodel.QuestionStoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class InCorrectAnswerFragment : Fragment(R.layout.fragment_incorrect_answer) {

    private lateinit var binding: FragmentIncorrectAnswerBinding
    private val questionStoryViewModel by viewModel<QuestionStoryViewModel>()
    private val userCorrectAnswersAdapter = UserCorrectAnswersAdapter(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Us.user.id?.let { questionStoryViewModel.getUserIncorrectAnswers(it) }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentIncorrectAnswerBinding.bind(view)

        questionStoryViewModel.userIncorrectAnswers.observe(viewLifecycleOwner) {
            it?.let { userCorrectAnswersAdapter.updateData(it) }
            setupView()
        }
    }

    private fun setupView() {
        binding.rvIncorrectAnswer.adapter = userCorrectAnswersAdapter
    }
}