package com.inquirypro.ui.main.fragments

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import com.inquirypro.R
import com.inquirypro.databinding.FragmentQuestionBinding
import com.inquirypro.model.Question
import com.inquirypro.ui.viewmodel.QuestionViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.inquirypro.base.BaseFragment
import com.inquirypro.databinding.CustomDialogBinding
import com.inquirypro.model.QuestionResult
import com.inquirypro.model.login.LoginResponse
import com.inquirypro.ui.main.callbacks.OpenQuestionFragmentListener
import com.inquirypro.ui.viewmodel.QuestionResultViewModel
import com.inquirypro.util.Constants.Companion.PART_ID
import com.inquirypro.util.Constants.Companion.QUESTION_RESULT_ID
import com.inquirypro.util.Constants.Companion.RESULT_CORRECT_INCORRECT_AMOUNT
import kotlinx.coroutines.launch


class QuestionFragment : BaseFragment(R.layout.fragment_question) {

    private lateinit var binding: FragmentQuestionBinding
    private val viewModel by viewModel<QuestionViewModel>()
    private val questionList: MutableList<Question> = mutableListOf()
    private val questionResultIdList: ArrayList<Int> = arrayListOf()
    private val questionResultViewModel by viewModel<QuestionResultViewModel>()
    private var openQuestionFragmentListener: OpenQuestionFragmentListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        openQuestionFragmentListener?.openQuestion()

        val partId = arguments?.getInt(PART_ID)

        partId?.let {
            viewModel.getQuestionsBySubsectionId(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentQuestionBinding.bind(view)


        viewModel.partQuestionLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                it.let { questionList.addAll(it) }
                Log.i("Questions", it.toString())
                updateUIWithData()
            }
        }

        binding.btnClose.setOnClickListener {
            showDialog()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OpenQuestionFragmentListener) {
            openQuestionFragmentListener = context
        }
    }

    private fun updateUIWithData() {

        var correctResult = 0
        var incorrectResult = 0

        val options: List<TextView> = listOf(
            binding.tvOption1,
            binding.tvOption2,
            binding.tvOption3,
            binding.tvOption4
        )

        if (questionList.isEmpty()) {
            return
        }

        var currentQuestionIndex = 0

        fun updateUIAndCheckAnswer(index: Int) {
            val currentQuestion = questionList[currentQuestionIndex]
            val correctOptionIndex = currentQuestion.correctOptionIndex
            val isCorrect = index == correctOptionIndex

            if (isCorrect) {
                correctResult++
                Toast.makeText(requireContext(), "Correct!", Toast.LENGTH_SHORT).show()
            } else {
                incorrectResult++
            }

            currentQuestionIndex++

            if (currentQuestionIndex < questionList.size) {

                updateUIWithQuestion(currentQuestionIndex)
            } else {

                val resultList = arrayListOf(incorrectResult, correctResult)
                val args = Bundle().apply {
                    putIntegerArrayList(RESULT_CORRECT_INCORRECT_AMOUNT, resultList)
                    putIntegerArrayList(QUESTION_RESULT_ID, questionResultIdList)
                }
                Log.i("questionResult", questionResultIdList.toString())

                Handler(Looper.getMainLooper()).postDelayed({
                    openQuestionFragmentListener?.closeQuestion()
                    findNavController().navigate(
                        R.id.action_questionFragment_to_questionResultFragment,
                        args
                    )
                }, 150)
            }
        }


        for ((index, option) in options.withIndex()) {

            option.text = questionList[currentQuestionIndex].options?.get(index).toString()
            binding.tvQuestionText.text = questionList[currentQuestionIndex].questionText
            option.setOnClickListener {
                lifecycleScope.launch {
                    val user = LoginResponse.retrieverUser()

                    val questionResult = QuestionResult(
                        question = questionList[currentQuestionIndex],
                        incorrectResult = options.indexOf(option),
                        user = user,
                        subsection = questionList[currentQuestionIndex].subsection
                    )
                    val createQuestionResult = questionResultViewModel.createQuestionResult(questionResult)

                    createQuestionResult?.id?.let { id -> questionResultIdList.add(id) }


                    Log.i("question in f", questionResult.toString())
                }

                updateUIAndCheckAnswer(index)
            }
            binding.tvQuestionText.text = questionList[currentQuestionIndex].questionText
        }
    }

    private fun updateUIWithQuestion(questionIndex: Int) {

        val question = questionList.getOrNull(questionIndex) ?: return

        binding.tvQuestionText.text = question.questionText

        val optionTextViews = listOf(
            binding.tvOption1,
            binding.tvOption2,
            binding.tvOption3,
            binding.tvOption4
        )

        optionTextViews.forEachIndexed { index, optionTextView ->
            optionTextView.text = question.options?.getOrNull(index) ?: ""
            val slideInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in)
            slideInAnimation.duration = 500
            optionTextView.startAnimation(slideInAnimation)
        }

        val slideInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in)
        slideInAnimation.duration = 500
        binding.tvQuestionText.startAnimation(slideInAnimation)
    }

    private fun showDialog() {
        val dialogBinding = CustomDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.btnYes.setOnClickListener {
            findNavController().popBackStack()
            dialog.dismiss()
            openQuestionFragmentListener?.closeQuestion()
        }

        dialogBinding.btnNo.setOnClickListener {
            dialog.dismiss()

        }
        dialog.show()
    }

    override fun handleOnBackPressed() {
        super.handleOnBackPressed()
        showDialog()
        openQuestionFragmentListener?.closeQuestion()

    }
}