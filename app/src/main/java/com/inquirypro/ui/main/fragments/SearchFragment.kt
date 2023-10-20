package com.inquirypro.ui.main.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.inquirypro.R
import com.inquirypro.base.BaseFragment
import com.inquirypro.databinding.FragmentSearchBinding
import com.inquirypro.ui.main.adapters.SearchAdapter
import com.inquirypro.ui.main.callbacks.SearchItemClickListener
import com.inquirypro.ui.viewmodel.CategoryViewModel
import com.inquirypro.ui.viewmodel.SharedViewModel
import com.inquirypro.util.Constants.Companion.CATEGORY_ID
import com.inquirypro.util.NavigationUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment(R.layout.fragment_search), SearchItemClickListener {

    private lateinit var binding: FragmentSearchBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val searchAdapter = SearchAdapter(this)

    private val categoryViewModel by viewModel<CategoryViewModel>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryViewModel.getCategories()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchBinding.bind(view)

        categoryViewModel.categoryList.observe(viewLifecycleOwner) {
            it?.let { categories -> searchAdapter.updateData(categories) }
        }

        sharedViewModel.enteredText.observe(viewLifecycleOwner) {
            searchAdapter.filter.filter(it)
        }
        setupView()
    }


    private fun setupView() {
        binding.rvSearchItem.adapter = searchAdapter
        searchAdapter.setupRecyclerView(binding.rvSearchItem)
    }

    override fun onClickPart(id: Int) {
        val bundle = Bundle().apply { putInt(CATEGORY_ID, id) }
        findNavController().navigate(R.id.action_homeFragment_to_partFragment, bundle,NavigationUtils.slideAnimLeft())
    }
}