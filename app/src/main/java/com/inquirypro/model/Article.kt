package com.inquirypro.model

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("imageUri")
    val imageUri: String
)
