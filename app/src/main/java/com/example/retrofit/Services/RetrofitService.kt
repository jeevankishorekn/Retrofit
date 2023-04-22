package com.example.retrofit.Services

import com.example.retrofit.Model.ImageDataItem
import com.example.retrofit.Model.PostDataItem
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {
    @GET("/photos")
    suspend fun getPhotos() : Response<List<ImageDataItem>>

    @GET("/photos/{id}")
    suspend fun getPhotosWithId(@Path("id") id : Int) : Response<ImageDataItem>

    @POST("/posts")
    suspend fun postPhoto(@Body postDataItem: PostDataItem) : Response<PostDataItem>
}