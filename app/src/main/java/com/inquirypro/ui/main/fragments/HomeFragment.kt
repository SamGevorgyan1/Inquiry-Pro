package com.inquirypro.ui.main.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.inquirypro.R
import com.inquirypro.databinding.FragmentHomeBinding
import com.inquirypro.model.Category
import com.inquirypro.ui.auth.fragments.Us
import com.inquirypro.ui.main.adapters.CategoryAdapter
import com.inquirypro.ui.viewmodel.CategoryViewModel
import com.inquirypro.ui.viewmodel.QuestionStoryViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val categoryAdapter = CategoryAdapter { itemListener(it) }
    private val viewModel by viewModel<CategoryViewModel>()
    private val questionResultViewModel by viewModel<QuestionStoryViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCategories()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        setupView()
        updateUiWithData()

        lifecycleScope.launch {
            Us.user.id?.let {
                binding.tvCorrectAnswersNumber.text =
                    questionResultViewModel.getUserCorrectAnswerAmount(it).toString()

                binding.tvIncorrectAnswersNumber.text =
                    questionResultViewModel.getUserIncorrectAnswerAmount(it).toString()
            }
        }
        setupListener()
    }

    private fun itemListener(category: Category) {

        val bundle = Bundle().apply { putParcelable("category", category) }

        val enterAnimation = R.anim.scale_in
        val exitAnimation = R.anim.scale_out

        val navOptions = NavOptions.Builder()
            .setEnterAnim(enterAnimation)
            .setExitAnim(exitAnimation)
            .build()

        findNavController().navigate(R.id.action_homeFragment_to_partFragment, bundle, navOptions)
    }

    private fun updateUiWithData() {
        viewModel.categoryList.observe(viewLifecycleOwner) {
            it?.let { categoryAdapter.updateData(it) }
        }
    }

    private fun setupView() {
        binding.rvCategory.adapter = categoryAdapter
    }

    private fun setupListener(){
        binding.tvCorrectAnswersNumber.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_correctAnswerFragment)
        }
        binding.tvIncorrectAnswersNumber.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_inCorrectAnswerFragment)
        }
    }
}