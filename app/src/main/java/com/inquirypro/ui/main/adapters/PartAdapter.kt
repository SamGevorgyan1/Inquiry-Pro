package com.inquirypro.ui.main.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inquirypro.databinding.AdapterPartBinding
import com.inquirypro.model.Part

class PartAdapter(val onClickListener: (partId: Int) -> Unit) :
    RecyclerView.Adapter<PartAdapter.ViewHolder>() {

    private val items: MutableList<Part> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(AdapterPartBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])


    override fun getItemCount(): Int = items.size

    @SuppressLint("notifyDataSetChanged")
    fun updateData(partList: List<Part>) {
        this.items.clear()
        partList.let { this.items.addAll(it) }
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: AdapterPartBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(part: Part) {
            binding.tvPartName.text = part.name

            binding.rvSubSection.setHasFixedSize(true)
            binding.rvSubSection.layoutManager = LinearLayoutManager(itemView.context)

            val adapter = SubsectionAdapter(onClickListener)
            part.subsections.let {
                if (it != null) {
                    adapter.updateData(it)
                }
            }
            binding.rvSubSection.adapter = adapter

            val isExpandable = part.isExpandable
            binding.rvSubSection.visibility = if (isExpandable) View.VISIBLE else View.GONE

            binding.constraintlayout.setOnClickListener {
            part.isExpandable = !part.isExpandable
            notifyItemChanged(adapterPosition)
            }
        }
        init {
            binding.tvPartName.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            when(v){
                //binding.tvPartName->items.getOrNull(adapterPosition)?.id?.let(onClickListener)
            }
        }
    }
}