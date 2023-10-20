package com.inquirypro.ui.main.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.inquirypro.R
import com.inquirypro.base.BaseFragment
import com.inquirypro.databinding.FragmentCategoryBinding
import com.inquirypro.ui.main.adapters.CategoryAdapter
import com.inquirypro.ui.main.adapters.CategoryPageAdapter
import com.inquirypro.ui.viewmodel.CategoryViewModel
import com.inquirypro.util.Constants.Companion.CATEGORY_ID
import com.inquirypro.util.NavigationUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryFragment : BaseFragment(R.layout.fragment_category) {

    private lateinit var binding: FragmentCategoryBinding
    private val categoryAdapter = CategoryPageAdapter { categoryId -> itemClickListener(categoryId) }
    private val viewModel by viewModel<CategoryViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoryBinding.bind(view)

        setupRecyclerView()
        observeCategoryList()
        viewModel.getCategories()
    }

    private fun itemClickListener(categoryId: Int) {
        val bundle = Bundle().apply { putInt(CATEGORY_ID, categoryId) }
        findNavController().navigate(R.id.partFragment, bundle, NavigationUtils.defaultScaleAnimation())
    }

    private fun observeCategoryList() {
        viewModel.categoryList.observe(viewLifecycleOwner) { categories ->
            categories?.let {
                categoryAdapter.updateData(it)
                for (category in it) {
                    Log.i("parts in category",category.parts.toString())
                    Log.i("category image",category.image.toString())
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvCategory.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = categoryAdapter
        }
    }
}