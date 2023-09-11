package com.inquirypro.api

import com.inquirypro.model.Employee
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface EmployeeApi {

    @POST("/employee/save")
    fun createQuestionStory(@Body employee: Employee): Call<Void>
}