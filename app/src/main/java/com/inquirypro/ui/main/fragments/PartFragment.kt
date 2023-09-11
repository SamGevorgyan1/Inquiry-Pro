package com.inquirypro.ui.main.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.inquirypro.R
import com.inquirypro.databinding.FragmentPartBinding
import com.inquirypro.model.Category
import com.inquirypro.model.Part
import com.inquirypro.ui.main.adapters.PartAdapter
import com.inquirypro.ui.viewmodel.PartViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PartFragment : Fragment(R.layout.fragment_part) {

    private lateinit var binding: FragmentPartBinding
    private val partAdapter = PartAdapter { itemListener(it) }
    private val viewModel by viewModel<PartViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("DEPRECATION")
        val category = arguments?.getParcelable<Category>("category")
        category?.id?.let { viewModel.getPartsByCategoryId(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPartBinding.bind(view)

        setupViews()
        updateUiWithData()
    }

    private fun updateUiWithData() {
        viewModel.partsLiveData.observe(viewLifecycleOwner) {
            it?.let { partAdapter.updateData(it) }
        }
    }

    private fun setupViews() {
        binding.rvPart.adapter = partAdapter
    }

    private fun itemListener(part: Part) {
        val bundle = Bundle().apply { putParcelable("part", part) }

        val enterAnimation = R.anim.scale_in
        val exitAnimation = R.anim.scale_out

        val navOptions = NavOptions.Builder()
            .setEnterAnim(enterAnimation)
            .setExitAnim(exitAnimation)
            .build()

        findNavController().navigate(
            R.id.action_partFragment_to_questionFragment, bundle, navOptions
        )
    }
}