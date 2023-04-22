package com.example.retrofit.Model

import java.io.Serializable

data class PostDataItem(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
) : Serializable