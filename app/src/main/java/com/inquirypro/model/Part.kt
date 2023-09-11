package com.inquirypro.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize

class Part(
    @SerializedName("category")
    val category: Category?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,

    @SerializedName("questions")
    val questions: @RawValue List<Question>

) : Parcelable