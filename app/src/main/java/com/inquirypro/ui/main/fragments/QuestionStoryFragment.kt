package com.inquirypro.ui.main.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.inquirypro.R
import com.inquirypro.databinding.FragmentQuestionStoryBinding
import com.inquirypro.ui.main.adapters.QuestionResultAdapter
import com.inquirypro.ui.viewmodel.QuestionStoryViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class QuestionStoryFragment : Fragment(R.layout.fragment_question_story) {

    private lateinit var binding: FragmentQuestionStoryBinding
    private val viewModel by viewModel<QuestionStoryViewModel>()
    private val questionResultAdapter = QuestionResultAdapter()
    private var questionStoryId by Delegates.notNull<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("questionStoryKey")?.let {
            questionStoryId = it
            viewModel.getQuestionStoryQuestions(it)
            Log.i("question adapter", it.toString())
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentQuestionStoryBinding.bind(view)

        lifecycleScope.launch {

            Log.i("idq", questionStoryId.toString())

            val questionStory = viewModel.getQuestionStoryById(questionStoryId)

            //binding.tvTitleCategoryName.text = questionStory?.category?.name
            //binding.tvTitlePartName.text = questionStory?.part?.name


            setupView()

        }
    }

    private fun setupView() {
        binding.rvQuestionResult.adapter = questionResultAdapter
    }
}