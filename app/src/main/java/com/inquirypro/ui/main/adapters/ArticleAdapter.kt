package com.inquirypro.ui.main.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inquirypro.databinding.AdapterArticleBinding
import com.inquirypro.model.Article

class ArticleAdapter(val onClickListener: ((id: Long) -> Unit)) :
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {


    private val items: MutableList<Article> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            AdapterArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])


    @SuppressLint("notifyDataSetChanged")
    fun updateData(articles: List<Article>) {
        items.clear()
        articles.let { items.addAll(it) }
        notifyDataSetChanged()
    }


    inner class ViewHolder(private val binding: AdapterArticleBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(article: Article) {
            binding.tvArticleName.text = article.name
        }

        init {
            binding.tvArticleName.setOnClickListener(this)
            binding.imageArticle.setOnClickListener(this)
            binding.articleItemLayout.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            items.getOrNull(adapterPosition)?.id?.let(onClickListener)
        }
    }
}