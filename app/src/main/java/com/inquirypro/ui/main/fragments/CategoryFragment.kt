package com.inquirypro.ui.main.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.inquirypro.R
import com.inquirypro.databinding.FragmentCategoryBinding
import com.inquirypro.model.Category
import com.inquirypro.ui.main.adapters.CategoryAdapter
import com.inquirypro.ui.viewmodel.CategoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryFragment : Fragment(R.layout.fragment_category) {

    private lateinit var binding: FragmentCategoryBinding
    private val categoryAdapter = CategoryAdapter{itemListener(it)}
    private val viewModel by viewModel<CategoryViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCategories()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoryBinding.bind(view)

        setupView()
        updateUiWithData()

    }

    private fun itemListener(category: Category) {

        val bundle = Bundle().apply { putParcelable("category", category) }

        val enterAnimation = R.anim.scale_in
        val exitAnimation = R.anim.scale_out

        val navOptions = NavOptions.Builder()
            .setEnterAnim(enterAnimation)
            .setExitAnim(exitAnimation)
            .build()

        findNavController().navigate(R.id.action_categoryFragment_to_partFragment, bundle,navOptions)
    }

    private fun updateUiWithData() {
        viewModel.categoryList.observe(viewLifecycleOwner) {
            it?.let { categoryAdapter.updateData(it) }
        }
    }

    private fun setupView() {
        binding.rvCategory.adapter = categoryAdapter
    }
}