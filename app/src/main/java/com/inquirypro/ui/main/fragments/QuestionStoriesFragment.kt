package com.inquirypro.ui.main.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.inquirypro.R
import com.inquirypro.databinding.FragmentQuestionStoriesBinding
import com.inquirypro.ui.auth.fragments.Us
import com.inquirypro.ui.main.adapters.QuestionStoryAdapter
import com.inquirypro.ui.viewmodel.QuestionStoryViewModel
import com.inquirypro.ui.viewmodel.StoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuestionStoriesFragment : Fragment(R.layout.fragment_question_stories) {

    private lateinit var binding: FragmentQuestionStoriesBinding
    private val viewModel by viewModel<StoryViewModel>()
    private val questionStoryAdapter = QuestionStoryAdapter { itemListener(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Us.user.id?.let { viewModel.getStoriesByUSerId(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentQuestionStoriesBinding.bind(view)

        viewModel.userStories.observe(viewLifecycleOwner) {
            it?.let { questionStoryAdapter.updateData(it) }


            it?.forEach { story ->
                //Log.i("User", story.user.toString())
                Log.i("Category", story.category.toString())
                Log.i("Part", story.part.toString())
            }
            setupView()
        }
    }

    private fun setupView() {
        binding.rvQuestionStory.adapter = questionStoryAdapter
    }

    private fun itemListener(id:Int) {

        val args = Bundle().apply {  putInt("questionStoryKey", id)  }

        findNavController().navigate(R.id.action_questionStoriesFragment_to_questionStoryFragment,args)


    }
}