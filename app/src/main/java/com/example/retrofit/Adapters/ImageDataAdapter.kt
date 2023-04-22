package com.example.retrofit.Adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.retrofit.Helpers.MainActivityHelper
import com.example.retrofit.Model.ImageDataItem
import com.example.retrofit.R

class ImageDataAdapter(private val dataSetPassed : List<ImageDataItem>, private val listener : MainActivityHelper) : RecyclerView.Adapter<ImageDataAdapter.ImageDataViewHolder>(){

    var dataSet = dataSetPassed

    class ImageDataViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var imgTitle : TextView
        var imgFromUrl : ImageView
        var cardId : CardView
        init{
            imgTitle = view.findViewById(R.id.imgTitle)
            imgFromUrl = view.findViewById(R.id.imgFromUrl)
            cardId = view.findViewById(R.id.cardViewId)
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
        val reqOptions : RequestOptions = RequestOptions.fitCenterTransform()
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_autorenew_24)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .encodeFormat(Bitmap.CompressFormat.PNG)

        Glide.with(holder.imgFromUrl).load(dataSet[position].url).apply(reqOptions).into(holder.imgFromUrl)
        holder.cardId.setOnClickListener{
            listener.performGetPhotoById(position+1)
        }
    }

    override fun getItemCount(): Int =dataSet.size

}