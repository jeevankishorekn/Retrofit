package com.example.retrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofit.Model.ImageDataItem

class ImageDataAdapter(private val dataSetPassed : List<ImageDataItem>) : RecyclerView.Adapter<ImageDataAdapter.ImageDataViewHolder>() {

    var dataSet = dataSetPassed

    class ImageDataViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var imgTitle : TextView
        var imgFromUrl : ImageView
        init{
            imgTitle = view.findViewById(R.id.imgTitle)
            imgFromUrl = view.findViewById(R.id.imgFromUrl)
        }
    }

    fun updateRecyclerView(data : List<ImageDataItem>){
        dataSet = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageDataViewHolder {

        var view = LayoutInflater.from(parent.context).inflate(R.layout.data_item,parent,false)
        return ImageDataViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageDataViewHolder, position: Int) {
        holder.imgTitle.text = dataSet[position].title
        Glide.with(holder.imgFromUrl).load(dataSet[position].url).into(holder.imgFromUrl)
    }

    override fun getItemCount(): Int =dataSet.size
}