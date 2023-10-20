package com.inquirypro.ui.main.fragments


import android.content.Context
import android.view.View
import com.inquirypro.R
import com.inquirypro.base.BaseFragment
import com.inquirypro.databinding.FragmentHomeBinding
import com.inquirypro.ui.main.MainActivity
import com.inquirypro.ui.main.adapters.CategoryAdapter
import com.inquirypro.ui.viewmodel.CategoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.inquirypro.model.login.LoginResponse
import com.inquirypro.ui.main.adapters.ArticleAdapter
import com.inquirypro.ui.main.callbacks.ButtonClickListener
import com.inquirypro.ui.viewmodel.ArticleViewModel
import com.inquirypro.ui.viewmodel.SharedViewModel
import com.inquirypro.util.Constants.Companion.ARTICLE_ID
import com.inquirypro.util.extensions.ViewExtension.hideViews
import com.inquirypro.util.extensions.ViewExtension.showViews
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    private val categoryViewModel by viewModel<CategoryViewModel>()
    private val articleViewModel by viewModel<ArticleViewModel>()

    private val categoryAdapter = CategoryAdapter { itemListener(it) }
    private val articleAdapter = ArticleAdapter { articleItemListener(it) }

    private var buttonClickListener: ButtonClickListener? = null

    private val sharedViewModel: SharedViewModel by activityViewModel()

    private fun articleItemListener(id: Long) {
        val bundle = Bundle().apply { putLong(ARTICLE_ID, id) }
        findNavController().navigate(R.id.action_homeFragment_to_readArticleFragment, bundle)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModels()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        setupView()
        observeCategoryList()
        observeArticleList()
        setupListener()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ButtonClickListener) {
            buttonClickListener = context
        }
    }

    private fun itemListener(categoryId: Int) {
        categoryViewModel.setCategoryId(categoryId)
        (activity as MainActivity).homeFragmentToPart(this.id, categoryId)
        //findNavController().navigate(R.id.action_homeFragment_to_partFragment, bundle, NavigationUtils.defaultScaleAnimation())
    }

    private fun setupListener() {

        binding.btnSearch.setOnClickListener {
            with(binding) {
                childFragmentManager.beginTransaction()
                    .replace(binding.container.id, SearchFragment())
                    .commit()
                svSearch.isIconified = false

                showViews(container, svSearch)

                hideViews(
                    btnSearch, tvCategoriesTitle, tvArticlesTitle, rvCategory, rvArticle,
                    tvTitleUserName, tvTextSeeAgain, btnSeeAllArticle, btnSeeAllCategory
                )

                svSearch.setOnCloseListener {
                    showViews(
                        btnSearch, tvCategoriesTitle, tvArticlesTitle, rvCategory, rvArticle,
                        tvTitleUserName, tvTextSeeAgain, btnSeeAllArticle, btnSeeAllCategory
                    )
                    hideViews(container, svSearch)

                    return@setOnCloseListener true
                }
            }
        }

        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) sharedViewModel.updateText(newText)
                return true
            }
        })

        binding.btnSeeAllArticle.setOnClickListener {
            buttonClickListener?.articleButton()
        }

        binding.btnSeeAllCategory.setOnClickListener {
            buttonClickListener?.categoryButton()
        }
    }

    private fun observeCategoryList() {
        categoryViewModel.categoryList.observe(viewLifecycleOwner) {
            it?.let { categoryAdapter.updateData(it) }
        }
    }

    private fun observeArticleList() {
        articleViewModel.articleLiveData.observe(viewLifecycleOwner) {
            it?.let { articleAdapter.updateData(it) }
        }
    }

    private fun setupView() {
        binding.rvCategory.adapter = categoryAdapter
        binding.rvArticle.adapter = articleAdapter
        categoryAdapter.setupContext(requireContext())
        val firstName = LoginResponse.retrieveLoginResponse()?.user?.firstName

        binding.tvTitleUserName.text = "Hi, $firstName"
    }

    private fun setupViewModels() {
        articleViewModel.getAllArticle()
        categoryViewModel.getCategories()
    }
}