package com.example.retrofit.Services

import com.example.retrofit.Model.ImageDataItem
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {
    @GET("/photos")
    suspend fun getPhotos() : Response<List<ImageDataItem>>

    @GET("/photos/{id}")
    suspend fun getPhotosWithId(@Path("id") id : Int) : Call<ImageDataItem>
}