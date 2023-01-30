package com.example.dsb_mobile.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dsb_mobile.R


/**
 * An adapter that provides a binding from an [ArrayList] of [BoatModel] to the [RecyclerView].
 *
 * @param newsList the list of boats to display
 */
class MyAdapter(private val newsList: ArrayList<com.example.dsb_mobile.data.model.BoatModel>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item,
            parent, false
        )

        return MyViewHolder(itemView)

    }

    /**
     * Binds the data from a [BoatModel] object to the item view at the given position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = newsList[position]
        //holder.titleImage.setImageResource(currentItem.titleImage)
        //var imageUrl="https://www.google.com/url?sa=i&url=http%3A%2F%2Ft1.gstatic.com%2Flicensed-image%3Fq%3Dtbn%3AANd9GcRYQC_v5bnrF4ScRVeYCGnfux2kt2GmjBXug_ezsi_i8aPyEMjxilhyQ_qzesxMmRFL9CjdoMZAMnefZDo&psig=AOvVaw2BMkX-CDFF1HOanhjpQsB9&ust=1675165167145000&source=images&cd=vfe&ved=0CA8QjRxqFwoTCLC9-taa7_wCFQAAAAAdAAAAABAE"
        // Glide.with(holder.itemView.context).load(imageUrl).into(holder.imageView)

        //lib used to set a image from a URL
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.imageView)
        holder.tvHeading.text = currentItem.heading

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {

        return newsList.size
    }

    /**
     * A [RecyclerView.ViewHolder] that holds a View for a [BoatModel].
     */
    class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.title_image)

        // val titleImage: ShapeableImageView = itemView.findViewById(R.id.title_image)
        val tvHeading: TextView = itemView.findViewById(R.id.tvHeading)
    }
}
