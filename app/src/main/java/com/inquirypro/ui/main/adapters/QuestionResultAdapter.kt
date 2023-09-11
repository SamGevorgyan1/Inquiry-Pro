package com.inquirypro.ui.main.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.inquirypro.databinding.AdapterQuestionResultBinding
import com.inquirypro.model.QuestionResult

class QuestionResultAdapter : RecyclerView.Adapter<QuestionResultAdapter.ViewHolder>() {

    private val items: MutableList<QuestionResult> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            AdapterQuestionResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    @SuppressLint("notifyDataSetChanged")
    fun updateData(questionList: List<QuestionResult>) {
        this.items.clear()
        questionList.let { this.items.addAll(it) }
        notifyDataSetChanged()
    }


    inner class ViewHolder(private val binding: AdapterQuestionResultBinding) :
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

            binding.tvQuestionText.text = questionResult.question?.questionText

            for ((index, option) in options.withIndex()) {
                if (questionResult.question?.options != null) {
                    if (index < questionResult.question.options.size) {
                        option.text = questionResult.question.options[index]

                       if (index==correct){
                           option.setBackgroundColor(Color.GREEN)
                       }

                      else if (index == inCorrect) {
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