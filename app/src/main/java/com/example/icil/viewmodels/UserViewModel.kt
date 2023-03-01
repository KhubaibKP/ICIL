package com.example.icil.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.icil.beans.DataResponse
import com.example.icil.retrofit.Api
import com.example.icil.retrofit.WebServiceFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel {

    val mutableLiveDataUser = MutableLiveData<DataResponse>()

    fun createEmployee(userName: String, password: String, fullName: String,
                       cnic: String, code: String, date: String, dept: String, desg: String, photo: String
                       ){

        val api: Api = WebServiceFactory.getRetrofit().create(Api::class.java)
        val call: Call<DataResponse> = api.createEmployee(userName, password, fullName, cnic, code, date, dept, desg, photo)
        call.enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                mutableLiveDataUser.value = response.body()
                Log.d("status", response.body()?.status.toString() + " "+ response.body()?.message)
            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {

            }

        })
    }
}