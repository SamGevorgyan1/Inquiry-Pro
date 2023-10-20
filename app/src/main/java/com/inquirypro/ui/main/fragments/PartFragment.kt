package com.inquirypro.ui.main.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.inquirypro.R
import com.inquirypro.base.BaseFragment
import com.inquirypro.databinding.FragmentPartBinding
import com.inquirypro.ui.main.adapters.PartAdapter
import com.inquirypro.ui.viewmodel.PartViewModel
import com.inquirypro.util.Constants.Companion.CATEGORY_ID
import com.inquirypro.util.Constants.Companion.PART_ID
import com.inquirypro.util.NavigationUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class PartFragment : BaseFragment(R.layout.fragment_part) {

    private lateinit var binding: FragmentPartBinding
    private val partAdapter = PartAdapter { partId -> itemClickListener(partId) }
    private val viewModel by viewModel<PartViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPartBinding.bind(view)

        val categoryId = arguments?.getInt(CATEGORY_ID)

        categoryId?.let { viewModel.getPartsByCategoryId(it) }


        setupViews()
        setupClickListener()
        observePartList()
    }

    private fun observePartList() {
        viewModel.partsLiveData.observe(viewLifecycleOwner) { parts ->
            parts?.let {
                partAdapter.updateData(it)

                for (part in it) {
                    Log.i("subsection in partF", part.subsections.toString())
                }
                if (parts.isNotEmpty()) {
                    binding.tvTitleCategoryName.text = parts[0].category?.name
                }
            }
        }
    }

    private fun setupViews() {
        binding.rvPart.adapter = partAdapter
    }

    private fun setupClickListener() {
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
    }

    private fun itemClickListener(subsectionId: Int) {

        val bundle = Bundle().apply { putInt(PART_ID, subsectionId) }

        findNavController().navigate(
            R.id.action_partFragment_to_questionFragment,
            bundle,
            NavigationUtils.defaultScaleAnimation()
        )
    }


    override fun handleOnBackPressed() {
        findNavController().popBackStack()
    }
}
