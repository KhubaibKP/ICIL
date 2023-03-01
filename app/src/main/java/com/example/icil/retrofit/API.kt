package com.example.icil.retrofit

import com.example.icil.beans.DataResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    @FormUrlEncoded
    @POST("Login")
    fun login(
        @Field("Username") Username: String?, @Field("Password") Password: String?
    ): Call<DataResponse>

    @FormUrlEncoded
    @POST("CreateEmployee")
    fun createEmployee(
        @Field("Username") Username: String?,
        @Field("Password") Password: String?,
        @Field("FullName") FullName: String?,
        @Field("CNIC") CNIC: String?,
        @Field("Code") Code: String?,
        @Field("DateOfJoining") DateOfJoining: String?,
        @Field("Department") Department: String?,
        @Field("Designation") Designation: String?,
        @Field("Photo") Photo: String?
        ): Call<DataResponse>




}