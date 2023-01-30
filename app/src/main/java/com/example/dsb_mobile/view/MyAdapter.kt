package com.example.dsb_mobile.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dsb_mobile.R
import com.google.android.material.imageview.ShapeableImageView

class MyAdapter(private val newsList: ArrayList<com.example.dsb_mobile.data.model.BoatModel>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item,
            parent, false
        )

        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = newsList[position]
        //holder.titleImage.setImageResource(currentItem.titleImage)
        //var imageUrl="https://www.google.com/url?sa=i&url=http%3A%2F%2Ft1.gstatic.com%2Flicensed-image%3Fq%3Dtbn%3AANd9GcRYQC_v5bnrF4ScRVeYCGnfux2kt2GmjBXug_ezsi_i8aPyEMjxilhyQ_qzesxMmRFL9CjdoMZAMnefZDo&psig=AOvVaw2BMkX-CDFF1HOanhjpQsB9&ust=1675165167145000&source=images&cd=vfe&ved=0CA8QjRxqFwoTCLC9-taa7_wCFQAAAAAdAAAAABAE"
       // Glide.with(holder.itemView.context).load(imageUrl).into(holder.imageView)
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.imageView)
        holder.tvHeading.text = currentItem.heading

    }


    override fun getItemCount(): Int {

        return newsList.size
    }

    class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.title_image)
       // val titleImage: ShapeableImageView = itemView.findViewById(R.id.title_image)
        val tvHeading: TextView = itemView.findViewById(R.id.tvHeading)
    }
}
