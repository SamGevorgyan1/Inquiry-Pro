package com.inquirypro.ui.main.fragments.article

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.inquirypro.R
import com.inquirypro.base.BaseFragment
import com.inquirypro.databinding.FragmentReadArticleBinding
import com.inquirypro.ui.viewmodel.ArticleViewModel
import com.inquirypro.util.Constants.Companion.ARTICLE_ID
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReadArticleFragment : BaseFragment(R.layout.fragment_read_article) {

    private lateinit var binding: FragmentReadArticleBinding
    private val viewModel by viewModel<ArticleViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReadArticleBinding.bind(view)

        val articleId = arguments?.getLong(ARTICLE_ID)

        lifecycleScope.launch {
            if (articleId != null) {
                val article = viewModel.getArticleById(articleId)

                binding.tvArticlesTitle.text = article?.name
                binding.tvArticleText.text = article?.description
            }
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun handleOnBackPressed() {
        findNavController().popBackStack()
    }
}