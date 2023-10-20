package com.inquirypro.ui.main.fragments.article

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.inquirypro.R
import com.inquirypro.base.BaseFragment
import com.inquirypro.databinding.FragmentArticleBinding
import com.inquirypro.ui.main.adapters.ArticleAdapter
import com.inquirypro.ui.viewmodel.ArticleViewModel
import com.inquirypro.util.Constants.Companion.ARTICLE_ID
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleFragment : BaseFragment(R.layout.fragment_article) {

    private lateinit var binding: FragmentArticleBinding
    private val viewModel by viewModel<ArticleViewModel>()
    private val articleAdapter = ArticleAdapter { itemClickListener(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAllArticle()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleBinding.bind(view)

        viewModel.articleLiveData.observe(viewLifecycleOwner) {
            it?.let { articles -> articleAdapter.updateData(articles) }
        }
        setupView()
    }

    private fun setupView() {
        binding.rvArticle.adapter = articleAdapter
    }

    private fun itemClickListener(id: Long) {

        val bundle = Bundle().apply { putLong(ARTICLE_ID, id) }

        findNavController().navigate(R.id.action_articleFragment_to_readArticleFragment, bundle)

    }
}