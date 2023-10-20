package com.inquirypro.ui.main.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inquirypro.databinding.AdapterHistoryBinding
import com.inquirypro.model.History
import java.text.SimpleDateFormat

class HistoryAdapter(val onClickListener: (subsectionId: Int) -> Unit) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private val items: MutableList<History> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            AdapterHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size


    @SuppressLint("notifyDataSetChanged")
    fun updateData(questionResult: List<History>) {
        this.items.clear()
        questionResult.let { this.items.addAll(it) }
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: AdapterHistoryBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(history: History) {


            binding.tvQuestionSize.text = "/${history.subsection?.questionsSize}"
            binding.tvSubsectionTitle.text = history.subsection?.name.toString()
            binding.tvTitleCategoryName.text = history.category?.name.toString()
            binding.tvTitlePartName.text = "/${history.part?.name.toString()}"
            binding.tvCorrectAnswer.text = history.lastPartCorrectAnswers.toString()

            val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
            val outputDateFormat = SimpleDateFormat("dd.MM.yyyy")

            try {
                val date = inputDateFormat.parse(history.createdAt)
                val formattedDate = outputDateFormat.format(date)
                binding.tvLocalDate.text = formattedDate.toString()
            } catch (e: Exception) {
                e.printStackTrace()

            }
        }

        init {
            binding.layoutQuestionStory.setOnClickListener(this)
            binding.tvTitlePartName.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            when (v) {
                binding.layoutQuestionStory -> items.getOrNull(adapterPosition)?.subsection?.id?.let(onClickListener)
            }
        }
    }
}