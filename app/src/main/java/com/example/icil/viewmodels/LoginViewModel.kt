package com.example.icil.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.icil.beans.App
import com.example.icil.beans.DataResponse
import com.example.icil.retrofit.Api
import com.example.icil.retrofit.WebServiceFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel {

    val mutableLiveDataLogin = MutableLiveData<DataResponse>()

    fun login(username: String, password: String){
        val api: Api = WebServiceFactory.getRetrofit().create(Api::class.java)
        val call: Call<DataResponse> = api.login(username, password)
        call.enqueue(object : Callback<DataResponse>{
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                mutableLiveDataLogin.value = response.body()
            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {

            }

        })

    }

}