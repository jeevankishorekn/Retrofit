package com.example.retrofit.Activity

import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.Adapters.ImageDataAdapter
import com.example.retrofit.Helpers.MainActivityHelper
import com.example.retrofit.R
import com.example.retrofit.RetrofitObjects.RetrofitObject
import com.example.retrofit.Services.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), MainActivityHelper {

    var isWifiConnected = false
    lateinit var recyclerView : RecyclerView
    lateinit var imageAdapter : ImageDataAdapter
    lateinit var retrofitServiceObject : RetrofitService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        retrofitServiceObject = RetrofitObject.retrofitObj.create(RetrofitService::class.java)
        isWifiConnectedInitially()
        if(!isWifiConnected){
            checkWifiConnection().show()
        }
        getNetworkStatus()
        recyclerView = findViewById(R.id.dataList)
        imageAdapter = ImageDataAdapter(mutableListOf(), this)
        recyclerView.adapter = imageAdapter
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

    }

    private fun checkWifiConnection(): AlertDialog {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Check your internet connection")
            .setMessage("You need to enable Wi-Fi in Settings to use this App")
            .setPositiveButton("Open Settings") { _, _ ->
                Intent(android.provider.Settings.ACTION_WIFI_SETTINGS).apply {
                    startActivity(this)
                }
            }
            .create()
        return alertDialog

    }

    fun fetchPhotoList(){
        lifecycleScope.launch(Dispatchers.IO) {
            val result = retrofitServiceObject.getPhotos()
            if (result.isSuccessful && result.body() != null) {
                withContext(Dispatchers.Main) {
                    imageAdapter.updateRecyclerView(result.body()!!)
                }
            }
        }
    }
    override fun performGetPhotoById(id : Int) {
        Log.d(TAG, "performGetPhotoById: Helloooooo")
        if (isWifiConnected) {
            lifecycleScope.launch(Dispatchers.IO) {
                val result = retrofitServiceObject.getPhotosWithId(id)

                if (result.isSuccessful && result.body() != null) {
                    withContext(Dispatchers.Main) {
                        Intent(this@MainActivity, SecondActivity::class.java).apply {
                            putExtra("ItemDataObject", result.body())
                            startActivity(this)
                        }
                    }
                }
            }
        }
    }

    fun getNetworkStatus(){
        var wifiMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        wifiMgr.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback(){
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                isWifiConnected = true
                fetchPhotoList()

            }

            override fun onLost(network: Network) {
                super.onLost(network)
                isWifiConnected = false
                lifecycleScope.launch(Dispatchers.Main){checkWifiConnection().show()}
            }

            override fun onUnavailable() {
                super.onUnavailable()
                isWifiConnected = false

            }

        })
    }

    private  fun isWifiConnectedInitially(){
        var wifiMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = wifiMgr.activeNetwork
        val capabilities = wifiMgr.getNetworkCapabilities(network)
        isWifiConnected = capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
    }


}