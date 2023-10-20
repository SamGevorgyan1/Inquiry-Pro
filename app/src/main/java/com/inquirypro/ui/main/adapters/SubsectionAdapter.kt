package com.inquirypro.ui.main.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inquirypro.databinding.AdapterSubsectionBinding
import com.inquirypro.model.Subsection

class SubsectionAdapter(val onClickListener: (subsectionId: Int) -> Unit) :
    RecyclerView.Adapter<SubsectionAdapter.ViewHolder>() {

    private val items: MutableList<Subsection> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            AdapterSubsectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    @SuppressLint("notifyDataSetChanged")
    fun updateData(subSectionList: List<Subsection>) {
        items.clear()
        subSectionList.let { items.addAll(it) }
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: AdapterSubsectionBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(subsection: Subsection) {
            binding.tvSubsectionName.text = subsection.name
        }

        init {
            binding.tvSubsectionName.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            when (v) {
                binding.tvSubsectionName -> items.getOrNull(adapterPosition)?.id?.let(onClickListener)
            }
        }
    }
}