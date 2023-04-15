package com.example.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView : RecyclerView
    lateinit var imageAdapter : ImageDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch(Dispatchers.IO) {
            val result = RetrofitObject.retrofitObj.getPhotos()
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