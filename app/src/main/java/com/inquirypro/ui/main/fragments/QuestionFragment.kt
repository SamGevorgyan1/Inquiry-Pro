package com.inquirypro.ui.main.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.inquirypro.R
import com.inquirypro.databinding.FragmentQuestionBinding
import com.inquirypro.model.Part
import com.inquirypro.model.Question
import com.inquirypro.ui.viewmodel.QuestionViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.inquirypro.model.QuestionResult
import com.inquirypro.model.Story
import com.inquirypro.ui.auth.fragments.Us
import com.inquirypro.ui.viewmodel.QuestionStoryViewModel
import com.inquirypro.ui.viewmodel.StoryViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException


class QuestionFragment : Fragment(R.layout.fragment_question) {

    private lateinit var binding: FragmentQuestionBinding
    private val viewModel by viewModel<QuestionViewModel>()
    private val questionList: MutableList<Question> = mutableListOf()
    private val questionStoryViewModel by viewModel<StoryViewModel>()
    private val questionStoryList2: ArrayList<Int> = arrayListOf()
    private val questionSViewModel by viewModel<QuestionStoryViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val part = arguments?.getParcelable<Part>("part")

        part?.id?.let { viewModel.getQuestionByCategoryId(it) }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentQuestionBinding.bind(view)


        viewModel.questionLiveData.observe(viewLifecycleOwner) {
            it?.let { questionList.addAll(it) }
            Log.i("Questions", it.toString())
            updateUIWithData()
        }
    }


    private fun updateUIWithData() {
        val questionResultList = ArrayList<QuestionResult>()
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
                    putIntegerArrayList("result", resultList)
                    putIntegerArrayList("resultQuestion", questionStoryList2)
                }
                val story = Story(user = Us.user, questionStories = questionResultList)
                Log.i("story list q",story.questionStories.toString())

                lifecycleScope.launch {
                    questionStoryViewModel.createQuestionStory(story)
                    Log.i("questionResult", questionResultList.toString())
                    findNavController().navigate(
                        R.id.action_questionFragment_to_questionResultFragment,
                        args
                    )
                }
            }
        }


        for ((index, option) in options.withIndex()) {
            option.text = questionList[currentQuestionIndex].options?.get(index).toString()
            binding.tvQuestionText.text = questionList[currentQuestionIndex].questionText
            option.setOnClickListener {

                val questionResult = QuestionResult(
                    question = questionList[currentQuestionIndex],
                    incorrectResult = options.indexOf(option),
                    user = Us.user
                )

                questionResultList.add(questionResult)

                lifecycleScope.launch {
                    val createQuestionStory = questionSViewModel.createQuestionStory(questionResult)
                    createQuestionStory?.id?.let { questionStoryList2.add(it) }
                }

                Log.i("questionList in qf", questionStoryList2.toString())

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



    fun addQuestionResultToStory(story: Story) {
        val client = OkHttpClient()

        // Convert the Story object to JSON format
        val json = Gson().toJson(story)
        val requestBody = RequestBody.create(MediaType.parse("application/json"), json)

        val request = Request.Builder()
            .url("http://192.168.1.10:9000/add-question-result")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (response.isSuccessful) {
                    // Handle successful response here
                    val responseBody = response.body()?.string()
                    // Parse the response if needed
                } else {
                    // Handle error response here
                    val errorMessage = response.body()?.string()
                    // Display or log the error message
                }
            }

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                // Handle network failure here
                e.printStackTrace()
            }
        })
    }
}