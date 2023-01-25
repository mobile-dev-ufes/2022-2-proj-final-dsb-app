package com.example.dsb_app.view.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.dsb_app.data.model.TeamModel
import com.example.dsb_app.databinding.TeamLineBinding

class ListTeamViewHolder(private val binding:TeamLineBinding): RecyclerView.ViewHolder(binding.root) {
    fun bindVH(team: TeamModel){
        binding.textProdName.text = "team.name" //mudar

    }

}