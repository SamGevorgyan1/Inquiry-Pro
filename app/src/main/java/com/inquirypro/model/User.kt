package com.inquirypro.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(
    val id:Int?=null,
    val name: String,
    val email: String,
    val password: String,
): Parcelable