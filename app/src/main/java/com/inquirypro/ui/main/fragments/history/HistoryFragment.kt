package com.inquirypro.ui.main.fragments.history

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.inquirypro.R
import com.inquirypro.base.BaseFragment
import com.inquirypro.databinding.FragmentHistoryBinding
import com.inquirypro.model.login.LoginResponse
import com.inquirypro.ui.main.adapters.HistoryAdapter
import com.inquirypro.ui.viewmodel.HistoryViewModel
import com.inquirypro.util.Constants
import com.inquirypro.util.Constants.Companion.SUBSECTION_ID
import com.inquirypro.util.NavigationUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : BaseFragment(R.layout.fragment_history) {

    private lateinit var binding: FragmentHistoryBinding
    private val viewModel by viewModel<HistoryViewModel>()
    private val historyAdapter = HistoryAdapter { partId -> itemClickListener(partId) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = LoginResponse.retrieverUser()
        user?.id?.let {
            Log.i("user id history", it.toString())
            viewModel.getHistoriesByUserId(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHistoryBinding.bind(view)

        viewModel.userStories.observe(viewLifecycleOwner) {
            it?.let { histories -> historyAdapter.updateData(histories) }
        }
        setupView()
    }

    private fun itemClickListener(subsectionId: Int) {
        val bundle = Bundle().apply { putInt(SUBSECTION_ID, subsectionId) }
        Log.i("subsection id history", subsectionId.toString())
        findNavController().navigate(
            R.id.action_partHistoryFragment_to_questionHistoryFragment,
            bundle,
            NavigationUtils.slideAnimLeft()
        )
    }

    private fun setupView() {
        binding.rvQuestionResultt.adapter = historyAdapter
    }
}