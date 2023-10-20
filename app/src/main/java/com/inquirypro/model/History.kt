package com.inquirypro.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class History(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("user")
    val user: User,

    @SerializedName("category")
    var category: Category? = null,

    @SerializedName("part")
    var part: Part? = null,

    @SerializedName("subsection")
    val subsection: Subsection? = null,

    @SerializedName("createdAt")
    val createdAt: String? = null,

    @SerializedName("questionResultList")
    val questionResultList: List<QuestionResult>?,

    @SerializedName("partIncorrectAnswers")
    val partIncorrectAnswers: Int? = null,

    @SerializedName("partCorrectAnswers")
    val partCorrectAnswers: Int? = null,

    @SerializedName("lastPartCorrectAnswers")
    val lastPartCorrectAnswers: Int? = null,

    @SerializedName("lastPartIncorrectAnswers")
    val lastPartIncorrectAnswers: Int? = null,
) : Parcelable



