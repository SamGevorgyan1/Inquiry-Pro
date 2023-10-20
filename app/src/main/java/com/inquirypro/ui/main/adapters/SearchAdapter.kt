package com.inquirypro.ui.main.adapters

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.inquirypro.databinding.AdapterSearchBinding
import com.inquirypro.databinding.AdapterSubsectionBinding
import com.inquirypro.model.Category
import com.inquirypro.ui.main.callbacks.SearchItemClickListener

class SearchAdapter(val searchItemClickListener: SearchItemClickListener? = null) : RecyclerView.Adapter<SearchAdapter.ViewHolder>(), Filterable {

    private val items: MutableList<Category> = mutableListOf()
    private var searchQuery: String = " "
    private var filteredItems: MutableList<Category> = mutableListOf()
    private lateinit var recyclerView: RecyclerView


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        AdapterSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = filteredItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(filteredItems[position], searchQuery)

    @Suppress("notifyDataSetChanged")
    fun updateData(categoryList: List<Category>) {
        this.items.clear()
        categoryList.let { items.addAll(it) }
        filter.filter(searchQuery)
        notifyDataSetChanged()
    }


    fun setupRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
    }

    inner class ViewHolder(private val bindIng: AdapterSearchBinding) :
        RecyclerView.ViewHolder(bindIng.root), View.OnClickListener {

        fun bind(category: Category, query: String) {

            bindIng.tvCategoryResult.text =
                category.name?.let { highlightSearchQuery(it, searchQuery) }

            category.parts?.forEach {
                bindIng.tvPartResult.text =
                    it.name?.let { partName -> highlightSearchQuery(partName, searchQuery) }
            }

            category.parts?.forEach { part ->
                part.subsections?.forEach { subsection ->
                    bindIng.tvSubsectionResult.text =
                        subsection.name?.let { highlightSearchQuery(it, query) }
                }
            }
        }

        init {
            bindIng.tvCategoryResult.setOnClickListener(this)
            bindIng.tvPartResult.setOnClickListener(this)
            bindIng.tvSubsectionResult.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            with(bindIng) {
                when (v) {
                    tvCategoryResult -> filteredItems.getOrNull(adapterPosition)?.parts?.getOrNull(adapterPosition)?.id.let {
                        if (it != null) {
                            searchItemClickListener?.onClickPart(it)
                        }
                    }
                }
            }
        }
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.trim() ?: ""
                val results = FilterResults()
                if (query.isEmpty()) {
                    results.values = mutableListOf<Category>()
                } else {
                    val filteredList = items.filter { item ->
                        val categoryMatches =
                            item.name?.contains(query, ignoreCase = true) ?: false

                        val partMatches = item.parts?.any { part ->
                            part.name?.contains(query, ignoreCase = true) ?: false
                        } ?: false


                        val subsectionMatches = item.parts?.any {
                            it.subsections?.any { subsection ->
                                subsection.name?.contains(query, ignoreCase = true) ?: false
                            } ?: false
                        } ?: false

                        categoryMatches || partMatches || subsectionMatches
                    }
                    results.values = filteredList
                }
                searchQuery = query
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                @Suppress("UNCHECKED_CAST")
                filteredItems = results?.values as MutableList<Category>

                if (filteredItems.isEmpty()) recyclerView.visibility = View.GONE
                else recyclerView.visibility = View.VISIBLE

                notifyDataSetChanged()
            }
        }
    }


    private fun highlightSearchQuery(fullText: String, query: String): CharSequence {
        val spannable = SpannableString(fullText)
        val words = fullText.split(" ")

        val startIndex = fullText.indexOf(query, ignoreCase = true)
        if (startIndex != -1) {
            val endIndex = startIndex + query.length
            spannable.setSpan(
                ForegroundColorSpan(Color.parseColor("#FDD835")),
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return spannable
    }
}