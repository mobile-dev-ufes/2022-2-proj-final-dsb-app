package com.example.dsb_app.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dsb_app.data.model.TeamModel
import com.example.dsb_app.databinding.TeamLineBinding
import com.example.dsb_app.view.listener.OnTeamListener
import com.example.dsb_app.view.viewHolder.ListTeamViewHolder

class ListTeamAdapter : RecyclerView.Adapter<ListTeamViewHolder>() {

    private var prodList: List<TeamModel> = listOf()
    private lateinit var listener: OnTeamListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListTeamViewHolder {
        val item = TeamLineBinding.inflate(LayoutInflater.from(parent.context),
            parent,false)

//        return ListTeamViewHolder(item, listener)
        return ListTeamViewHolder(item)
    }

    override fun onBindViewHolder(holder: ListTeamViewHolder, position: Int) {
        holder.bindVH(prodList[position])
    }

    override fun getItemCount(): Int {
        return prodList.count()
    }

    fun updateProdList(list: List<TeamModel>) {
        prodList = list
        notifyDataSetChanged()
    }

    fun setListener(productListener: OnTeamListener) {
        listener = productListener
    }

}