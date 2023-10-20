package com.inquirypro.ui.main.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.inquirypro.R
import com.inquirypro.base.BaseFragment
import com.inquirypro.databinding.FragmentQuestionResultBinding
import com.inquirypro.model.QuestionResult
import com.inquirypro.ui.main.adapters.QuestionResultAdapter
import com.inquirypro.ui.viewmodel.CategoryViewModel
import com.inquirypro.ui.viewmodel.QuestionResultViewModel
import com.inquirypro.util.Constants.Companion.CATEGORY_ID
import com.inquirypro.util.Constants.Companion.QUESTION_RESULT_ID
import com.inquirypro.util.Constants.Companion.RESULT_CORRECT_INCORRECT_AMOUNT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuestionResultFragment : BaseFragment(R.layout.fragment_question_result) {

    private lateinit var binding: FragmentQuestionResultBinding
    private val questionResultAdapter = QuestionResultAdapter()
    private val questionResultViewModel by viewModel<QuestionResultViewModel>()
    private val categoryViewModel by viewModel<CategoryViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentQuestionResultBinding.bind(view)


        val incorrectResult =
            arguments?.getIntegerArrayList(RESULT_CORRECT_INCORRECT_AMOUNT)?.get(0).toString()
        val correctResult =
            arguments?.getIntegerArrayList(RESULT_CORRECT_INCORRECT_AMOUNT)?.get(1).toString()


        binding.tvInCorrectResult.text = incorrectResult
        binding.tvCorrectResult.text = correctResult


        val questionResultIds = arguments?.getIntegerArrayList(QUESTION_RESULT_ID)

        Log.i("question in ques ", questionResultIds.toString())

        val questionResults = mutableListOf<QuestionResult>()

        questionResultIds?.forEach { questionId ->
            lifecycleScope.launch {
                val questionResult = fetchQuestionResult(questionId)
                Log.i("question result in qf ", questionResult.toString())
                if (questionResult != null) {
                    questionResults.add(questionResult)
                    questionResultAdapter.updateData(questionResults)
                    binding.tvTittleSectionName.text = questionResult.subsection?.name

                } else {
                    Log.e("QuestionResultFragment", "QuestionResult is null for ID: $questionId")
                }
            }
        }
        categoryViewModel.categoryId.observe(viewLifecycleOwner){
            Log.i("category viewmodel", it.toString())
        }

        setupListener()
        setupView()
    }

    private fun setupView() {
        binding.rvQuestionResult.adapter = questionResultAdapter
    }

    private suspend fun fetchQuestionResult(questionId: Int): QuestionResult? {
        return withContext(Dispatchers.IO) {
            questionResultViewModel.getQuestionResultById(questionId)
        }
    }

    override fun handleOnBackPressed() {

        findNavController().navigate(R.id.action_questionResultFragment_to_partFragment)
    }

    private fun setupListener() {


    }
}
