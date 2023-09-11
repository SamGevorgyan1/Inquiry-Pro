package com.inquirypro.ui.main.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.inquirypro.R
import com.inquirypro.databinding.FragmentQuestionResultBinding
import com.inquirypro.model.QuestionResult
import com.inquirypro.ui.auth.fragments.Us
import com.inquirypro.ui.main.adapters.QuestionResultAdapter
import com.inquirypro.ui.viewmodel.QuestionStoryViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class QuestionResultFragment : Fragment(R.layout.fragment_question_result) {

    private lateinit var binding: FragmentQuestionResultBinding
    private val questionResultAdapter = QuestionResultAdapter()
    private val questionStoryViewModel by viewModel<QuestionStoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Us.user.id?.let { questionStoryViewModel.getQuestionStoryByUserId(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentQuestionResultBinding.bind(view)

        binding.tvInCorrectResult.text = arguments?.getIntegerArrayList("result")?.get(0).toString()
        binding.tvCorrectResult.text = arguments?.getIntegerArrayList("result")?.get(1).toString()


        lifecycleScope.launch {
            val questionResult = arguments?.getIntegerArrayList("resultQuestion")

            val newQuestionStories =
                mutableListOf<QuestionResult>()

            questionResult?.forEach { questionId ->
                val questionStoryById = questionStoryViewModel.getQuestionStoryById(questionId)

                if (questionStoryById != null) {
                    newQuestionStories.add(questionStoryById)
                } else {
                    Log.e("questionId", "QuestionResult is null for id: $questionId")
                }
            }
            questionResultAdapter.updateData(newQuestionStories)
        }
        setupView()
    }

    private fun setupView() {
        binding.rvQuestionResult.adapter = questionResultAdapter
    }
}