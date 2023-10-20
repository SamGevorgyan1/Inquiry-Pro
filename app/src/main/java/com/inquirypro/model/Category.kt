package com.inquirypro.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.grpc.internal.SharedResourceHolder.Resource
import kotlinx.parcelize.Parcelize

@Parcelize
class Category(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("parts")
    val parts: List<Part>?,

    @SerializedName("image")
    val image: String?,

) : Parcelable
