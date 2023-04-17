package com.example.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.Adapters.ImageDataAdapter
import com.example.retrofit.RetrofitObjects.RetrofitObject
import com.example.retrofit.Services.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView : RecyclerView
    lateinit var imageAdapter : ImageDataAdapter
    lateinit var retrofitServiceObject : RetrofitService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retrofitServiceObject = RetrofitObject.retrofitObj.create(RetrofitService::class.java)

        lifecycleScope.launch(Dispatchers.IO) {
            val result = retrofitServiceObject.getPhotos()
            if(result.isSuccessful && result.body() != null) {
                withContext(Dispatchers.Main) {
                    imageAdapter.updateRecyclerView(result.body()!!)
                }
            }
        }

        recyclerView = findViewById(R.id.dataList)
        imageAdapter = ImageDataAdapter(mutableListOf())
        recyclerView.adapter = imageAdapter
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

    }


}