package com.inquirypro.ui.main.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.inquirypro.databinding.AdapterUserCorrectAnswerBinding
import com.inquirypro.model.QuestionResult

class UserCorrectAnswersAdapter(val isCorrect: Boolean) : RecyclerView.Adapter<UserCorrectAnswersAdapter.ViewHolder>() {

    private val items: MutableList<QuestionResult> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            AdapterUserCorrectAnswerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    @SuppressLint("notifyDataSetChanged")
    fun updateData(questionResultList: List<QuestionResult>) {
        this.items.clear()
        questionResultList.let { this.items.addAll(it) }
        notifyDataSetChanged()
    }


    inner class ViewHolder(private val binding: AdapterUserCorrectAnswerBinding) :
        RecyclerView.ViewHolder(binding.root) {



        fun bind(questionResult: QuestionResult) {


            val options: List<TextView> = listOf(
                binding.tvOption1,
                binding.tvOption2,
                binding.tvOption3,
                binding.tvOption4
            )

            val inCorrect = questionResult.incorrectResult
            val correct = questionResult.correctResult

            for ((index, option) in options.withIndex()) {
                if (questionResult.question?.options != null) {
                    if (index < questionResult.question.options.size) {
                        option.text = questionResult.question.options[index]

                        if (index==correct&&isCorrect){
                            option.setBackgroundColor(Color.GREEN)
                        }

                        if (index == inCorrect&&!isCorrect) {
                            option.setBackgroundColor(Color.RED)
                        }
                    } else {
                        option.text = ""
                    }
                }
            }
        }
    }
}