package com.inquirypro.ui.main.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inquirypro.model.Category
import com.inquirypro.databinding.AdapterCategoryBinding

class CategoryAdapter(val onClickListener: ((categoryId: Int) -> Unit)) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private val items: MutableList<Category> = mutableListOf()

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            AdapterCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("notifyDataSetChanged")
    fun updateData(categoryList: List<Category>) {
        this.items.clear()
        categoryList.let { this.items.addAll(it) }
        notifyDataSetChanged()
    }

    fun setupContext(context: Context) {
        this.context = context
    }


    inner class ViewHolder(private val binding: AdapterCategoryBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(category: Category) {
            binding.tvCategoryName.text = category.name
            binding.tvPartCount.text = "${category.parts?.size} Parts"
        }

        init {
            binding.tvCategoryName.setOnClickListener(this)
            binding.categoryItemLayout.setOnClickListener(this)
            binding.ivCategory.setOnClickListener(this)
            binding.tvPartCount.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            items.getOrNull(adapterPosition)?.id?.let(onClickListener)
        }
    }
}