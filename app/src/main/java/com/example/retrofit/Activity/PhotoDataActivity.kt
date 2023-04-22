package com.example.retrofit.Activity

import android.content.ContentValues.TAG
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.retrofit.Model.PostDataItem
import com.example.retrofit.R
import com.example.retrofit.RetrofitObjects.RetrofitObject
import com.example.retrofit.Services.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import kotlin.math.log

class PhotoDataActivity : AppCompatActivity() {

    lateinit var postAlbumId: TextView
    lateinit var postID: TextView
    lateinit var postTitle: TextView
    lateinit var postButton: Button
    lateinit var retrofitServiceObject: RetrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_data)
        postButton = findViewById(R.id.postButton)
        retrofitServiceObject = RetrofitObject.retrofitObj.create(RetrofitService::class.java)
        postAlbumId = findViewById(R.id.postAlbumID)
        postID = findViewById(R.id.postId)
        postTitle = findViewById(R.id.postTitle)

        postButton.setOnClickListener {
            post()
        }
    }

    private fun post() {
        lifecycleScope.launch(Dispatchers.IO) {
            val postCall = retrofitServiceObject.postPhoto(
                PostDataItem(
                    "test",
                    postID.text.toString().toInt(),
                    postTitle.text.toString(),
                    postAlbumId.text.toString().toInt()
                )
            )
            if (postCall.isSuccessful && postCall.body() != null) {
                if (postCall.code() == 201) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@PhotoDataActivity,
                            "POST SUCCESSFUL",
                            Toast.LENGTH_SHORT
                        ).show()
                        AlertDialog.Builder(this@PhotoDataActivity)
                            .setTitle("POST Successful")
                            .setPositiveButton("OK"){_,_ ->}
                            .setMessage("Request:\n ${postCall.body().toString()} \n Status Code: ${postCall.code()}")
                            .create()
                            .show()
                        postID.text = ""
                        postTitle.text = ""
                        postAlbumId.text = ""
                    }
                }
            } else Toast.makeText(
                this@PhotoDataActivity,
                "POST failed. Please try again!",
                Toast.LENGTH_SHORT
            ).show()

        }
    }
}