package com.inquirypro.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuestionResult(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("user")
    val user: User? = null,

    @SerializedName("partv")
    val part: Part? = null,

    @SerializedName("subsection")
    val subsection: Subsection? = null,

    @SerializedName("question")
    val question: Question? = null,

    @SerializedName("correctResult")
    val correctResult: Int? = null,

    @SerializedName("incorrectResult")
    val incorrectResult: Int,

    @SerializedName("createdAt")
    val createdAt: String? = null

) : Parcelable