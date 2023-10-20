package com.inquirypro.ui.main.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.inquirypro.R
import com.inquirypro.databinding.AdapterQuestionResultBinding
import com.inquirypro.model.QuestionResult

class QuestionResultAdapter : RecyclerView.Adapter<QuestionResultAdapter.ViewHolder>() {

    private val items: MutableList<QuestionResult> = mutableListOf()
    private var isExpanded = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            AdapterQuestionResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

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
            binding.tvTextExplanation.text = questionResult.question?.explanation

            for ((index, option) in options.withIndex()) {
                if (questionResult.question?.options != null) {
                    if (index < questionResult.question.options.size) {
                        option.text = questionResult.question.options[index]

                        if (index == correct) {
                            option.setBackgroundColor(Color.GREEN)
                        } else if (index == inCorrect) {
                            option.setBackgroundColor(Color.RED)
                        }
                    } else {
                        option.text = ""
                    }
                }
            }
        }

        init {
            binding.tvTextExplanation.setOnClickListener(this)
            binding.btnShowExplanation.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            when (v) {

                binding.btnShowExplanation -> {
                    toggleTextViewExpansion()
                }

                binding.tvTextExplanation -> {
                    toggleTextViewExpansion()
                }
            }
        }

        private fun toggleTextViewExpansion() {
            if (isExpanded) {
                binding.tvTextExplanation.maxLines = 1
                binding.btnShowExplanation.setBackgroundResource(R.drawable.ic_arrow_down)
                binding.tvTextExplanation.ellipsize = TextUtils.TruncateAt.END
            } else {
                binding.tvTextExplanation.maxLines = Integer.MAX_VALUE
                binding.btnShowExplanation.setBackgroundResource(R.drawable.ic_arrow_up)
                binding.tvTextExplanation.ellipsize = null
            }
            isExpanded = !isExpanded
        }
    }
}