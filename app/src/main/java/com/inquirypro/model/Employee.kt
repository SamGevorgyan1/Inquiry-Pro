package com.inquirypro.model

import com.google.gson.annotations.SerializedName

data class Employee(
    @SerializedName("id")
    val id:Int,
    @SerializedName("name")
    val name:String,
    @SerializedName("location")
    val location:String,
    @SerializedName("branch")
    val branch:String

)
