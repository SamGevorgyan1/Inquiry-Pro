package com.inquirypro.ui.main.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inquirypro.databinding.AdapterQuestionBinding
import com.inquirypro.model.Question

class QuestionAdapter : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    private val items: MutableList<Question> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =

        ViewHolder(AdapterQuestionBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int =items.size

    @SuppressLint("notifyDataSetChanged")
    fun updateData(questionList:List<Question>){
        this.items.clear()
        questionList.let { this.items.addAll(it) }
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: AdapterQuestionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(question: Question) {



        }
    }
}