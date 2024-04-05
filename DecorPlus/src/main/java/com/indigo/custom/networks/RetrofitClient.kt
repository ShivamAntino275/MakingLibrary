package com.indigo.custom.networks

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    companion object{
      private var instance:ApiService ?= null
       fun getApiService():ApiService{
           if (instance==null){

               instance = Retrofit.Builder()
                   .baseUrl(AppUrl)
                   .addConverterFactory(GsonConverterFactory.create())
                   .client(
                       OkHttpClient.Builder()
                       .readTimeout(1, TimeUnit.MINUTES)
                       .connectTimeout(1, TimeUnit.MINUTES)
                       .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build())
                   .build().create(ApiService::class.java)
           }
           return instance!!
       }
    }
}