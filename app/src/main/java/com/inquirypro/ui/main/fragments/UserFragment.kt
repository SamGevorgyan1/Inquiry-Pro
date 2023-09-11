package com.inquirypro.ui.main.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.inquirypro.R
import com.inquirypro.api.RetrofitService
import com.inquirypro.databinding.FragmentUserBinding
import com.inquirypro.model.QuestionResult
import com.inquirypro.model.Story
import com.inquirypro.ui.auth.fragments.Us
import com.inquirypro.ui.main.adapters.QuestionResultAdapter
import com.inquirypro.ui.viewmodel.QuestionStoryViewModel
import com.inquirypro.ui.viewmodel.StoryViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

class UserFragment : Fragment(R.layout.fragment_user) {

    private lateinit var binding: FragmentUserBinding
    private val viewModel by viewModel<QuestionStoryViewModel>()
    private val storyViewModel by viewModel<StoryViewModel>()
    private val questionResultAdapter = QuestionResultAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getQuestionStoryQuestions(10)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserBinding.bind(view)

        lifecycleScope.launch {
            val questionStoryById1 = viewModel.getQuestionStoryById(104)
            val questionStoryById2 = viewModel.getQuestionStoryById(105)
            val questionStoryById3 = viewModel.getQuestionStoryById(106)

            val questionResult = arrayListOf<QuestionResult>()
            questionStoryById1?.let { questionResult.add(it) }
            questionStoryById2?.let { questionResult.add(it) }
            questionStoryById3?.let { questionResult.add(it) }

            Log.i("questionsResult in user", questionResult.toString())

            if (questionResult.isNotEmpty()) {
                val story = Story(Us.user, questionResult)


                RetrofitService.getStoryApiService().addQuestionResultToStory(story).enqueue(object :Callback<Story>{
                    override fun onResponse(call: Call<Story>, response: Response<Story>) {
                        if (response.isSuccessful) Log.i("respp","successful")
                        else  Log.i("respp","not successful")
                    }

                    override fun onFailure(call: Call<Story>, t: Throwable) {
                        Log.i("erorr",t.message.toString())
                    }

                })

                val json = Gson().toJson(story)
                val jsonData = JSONObject(json)

                val isValid: Boolean = validateAgainstSchema(jsonData)
                if (isValid) {

                    Log.i("Json","valid")
                } else {
                    Log.i("Json","not valid")
                }
            } else {
                Log.i("is empty", "no")
            }
        }
    }

    private fun setupView() {
        binding.stories.adapter = questionResultAdapter
    }

    // Function to validate JSON data against a schema (you need to implement this)
    private fun validateAgainstSchema(jsonData: JSONObject): Boolean {
        // Implement your validation logic here
        return true // Return true if data is valid, false otherwise
    }
}
