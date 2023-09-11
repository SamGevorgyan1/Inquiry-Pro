package com.inquirypro.model

import com.google.gson.annotations.SerializedName

data class Story(
    @SerializedName("user")
    val user: User,

    @SerializedName("questionStories")
    val questionStories: List<QuestionResult>?
) {
    @SerializedName("id")
    val id: Int? = null

    @SerializedName("name")
    val name: String? = null

    @SerializedName("category")
    var category: Category? = null

    @SerializedName("part")
    var part: Part? = null

    @SerializedName("correctAnswers")
    val correctAnswers: Int? = null

    @SerializedName("incorrectAnswers")
    val incorrectAnswers: Int? = null

    @SerializedName("createdAt")
    val createdAt: String? = null


    init {
        questionStories?.get(0)?.question?.part?.category.let { this.category = it }
        questionStories?.get(0)?.question?.part?.let { this.part = it }
    }
}
