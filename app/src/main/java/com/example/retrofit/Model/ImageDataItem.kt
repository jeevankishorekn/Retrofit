package com.example.retrofit.Model

import java.io.Serializable

data class ImageDataItem(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
) : Serializable