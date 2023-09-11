package com.inquirypro.ui.main.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inquirypro.databinding.AdapterQuestionStoryBinding
import com.inquirypro.model.Story

class QuestionStoryAdapter(private val onClickListener: ((id: Int) -> Unit)) :
    RecyclerView.Adapter<QuestionStoryAdapter.ViewHolder>() {

    private val items: MutableList<Story> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            AdapterQuestionStoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size


    @SuppressLint("notifyDataSetChanged")
    fun updateData(storyList: List<Story>) {
        this.items.clear()
        storyList.let { this.items.addAll(it) }
        notifyDataSetChanged()

    }

    inner class ViewHolder(private val binding: AdapterQuestionStoryBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(story: Story) {
            // binding.tvQuestionText.text = questionResult.question?.questionText
            binding.tvResultCorrect.text = story.correctAnswers.toString()
            binding.tvResultInCorrect.text = story.incorrectAnswers.toString()
            binding.tvLocalDate.text = story.createdAt


            binding.tvPartName.text = story.part?.name
            binding.tvCategoryName.text = story.category?.name
        }

        init {
            binding.layoutQuestionStory.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            when (v) {
                binding.layoutQuestionStory -> items.getOrNull(adapterPosition)?.id?.let(
                    onClickListener
                )
            }
        }
    }
}