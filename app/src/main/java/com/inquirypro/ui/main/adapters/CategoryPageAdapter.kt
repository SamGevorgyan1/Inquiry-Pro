package com.inquirypro.ui.main.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inquirypro.databinding.AdapterCategoryPageBinding
import com.inquirypro.model.Category
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class CategoryPageAdapter(val onClickListener: ((categoryId: Int) -> Unit)) :
    RecyclerView.Adapter<CategoryPageAdapter.ViewHolder>() {

    private val items: MutableList<Category> = mutableListOf()

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            AdapterCategoryPageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
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


    inner class ViewHolder(private val binding: AdapterCategoryPageBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(category: Category) {

            binding.tvTitleCategoryName.text = category.name
            binding.tvCountParts.text = "${category.parts?.size} Parts"
            Picasso.get()
                .load(category.image)
                .into(binding.ivIconCategory, object : Callback {
                    override fun onSuccess() {
                       Log.i("image","success ")
                    }

                    override fun onError(e: Exception?) {
                        Log.i("image","not success $e")
                    }
                })
        }

        init {
            binding.tvTitleCategoryName.setOnClickListener(this)
            binding.categoryItemLayout.setOnClickListener(this)
            binding.ivIconCategory.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            items.getOrNull(adapterPosition)?.id?.let(onClickListener)
            items.getOrNull(adapterPosition)?.parts?.forEach {
                Log.i("parts name", it.name.toString())
                it.subsections?.forEach { subsection ->
                    Log.i("subsection name", subsection.name.toString())
                }
            }
        }
    }
}