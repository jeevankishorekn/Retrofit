package com.example.retrofit

import com.example.retrofit.Model.ImageDataItem
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitService {
    @GET("/photos")
    suspend fun getPhotos() : Response<List<ImageDataItem>>
}