package com.inquirypro.ui.main.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inquirypro.model.Category
import com.inquirypro.databinding.AdapterCategoryBinding


class CategoryAdapter(val onClickListener: ((category: Category) -> Unit)) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private val items: MutableList<Category> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            AdapterCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    @SuppressLint("notifyDataSetChanged")
    fun updateData(categoryList: List<Category>) {
        this.items.clear()
        categoryList.let { this.items.addAll(it) }
        notifyDataSetChanged()
    }


    inner class ViewHolder(private val binding: AdapterCategoryBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(category: Category) {
            binding.tvCategoryName.text = category.name
            binding.tvCategoryDescription.text = category.description
        }
        init {
            binding.tvCategoryName.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            when(v){
                binding.tvCategoryName -> items.getOrNull(adapterPosition)?.let(onClickListener)
            }
        }
    }
}