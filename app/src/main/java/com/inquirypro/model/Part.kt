package com.inquirypro.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Part(

    @SerializedName("category")
    val category: Category? = null,

    @SerializedName("id")
    val id: Int? = 0,

    @SerializedName("name")
    val name: String? = "Part name",

    @SerializedName("subsections")
    val subsections: List<Subsection>? = null,


    var isExpandable: Boolean = false
) : Parcelable