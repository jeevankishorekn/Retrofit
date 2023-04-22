package com.example.retrofit.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.retrofit.Model.ImageDataItem
import com.example.retrofit.R

class SecondActivity : AppCompatActivity() {

    lateinit var imageWithId : ImageView
    lateinit var itemId : TextView
    lateinit var itemTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second2)
        imageWithId = findViewById(R.id.imgWithId)
        itemId = findViewById(R.id.itemId)
        itemTitle = findViewById(R.id.itemTitle)

        val itemObj = intent.getSerializableExtra("ItemDataObject") as ImageDataItem

        Glide.with(imageWithId).load(itemObj.url).into(imageWithId)
        itemId.text = itemObj.id.toString()
        itemTitle.text = itemObj.title.toString()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    private fun openSettings(){
        val service = Intent(android.provider.Settings.ACTION_SETTINGS).apply {
            startActivity(this)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_settings -> {
                openSettings()
                true
            }
            R.id.post -> {
                Intent(this@SecondActivity,PhotoDataActivity::class.java).apply { startActivity(this) }
                return true
            }
            R.id.dummy2 -> {
                Toast.makeText(this@SecondActivity,"Dummy 2 menu item", Toast.LENGTH_SHORT).show()
                true
            }
            else -> false
        }


    }
}