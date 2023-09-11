package com.inquirypro.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
class Question(
    @SerializedName("id")
    val id: Int,

    @SerializedName("questionText")
    var questionText: String,

    @SerializedName("options")
    val options: List<String>?,

    @SerializedName("correctOptionIndex")
    val correctOptionIndex: Int,

    @SerializedName("part")
    val part:Part
): Parcelable