package com.inquirypro.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Subsection(

    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("questions")
    val questions: List<Question>?,


    @SerializedName("part")
    val part: Part,


    @SerializedName("questionsSize")
    val questionsSize: Int,


    ) : Parcelable
