package com.example.icil.retrofit;

import android.content.Context;
import android.view.View;

import com.example.icil.beans.App;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RetrofitNetworkHandling<T> implements Callback<T> {

    private final Context context;
    private View view;
    private final ResponseCallback<T> listener;

    public RetrofitNetworkHandling(ResponseCallback<T> listener) {
        this.context = App.getApplication();
        this.listener = listener;
    }

    public RetrofitNetworkHandling(View view, ResponseCallback<T> listener) {
        this.context = App.getApplication();
        this.view = view;
        this.listener = listener;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        //TODO mainactivity- check this loading finish for possible exceptions





    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        t.printStackTrace();





    }


    public interface ResponseCallback<T> {
        void onSuccess(Call<T> call, T response);

        void onFail(Call<T> call, T response);

        void onError(Call<T> call, T response);


    }
}