package com.umut.appsport.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umut.appsport.databinding.ItemStandingBinding
import com.umut.appsport.model.Standing

class RankAdapter : RecyclerView.Adapter<RankAdapter.RankViewHolder>() {

    private val standingsList = mutableListOf<Standing>()

    fun submitList(list: List<Standing>) {
        standingsList.clear()
        standingsList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankViewHolder {

        val binding = ItemStandingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RankViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RankViewHolder, position: Int) {
        val standing = standingsList[position]


        holder.binding.tvRank.text = standing.rank.toString()
        holder.binding.tvTeamName.text = standing.team.name
        holder.binding.tvPlayed.text = standing.all.played.toString()
        holder.binding.tvWin.text = standing.all.win.toString()
        holder.binding.tvDraw.text = standing.all.draw.toString()
        holder.binding.tvLose.text = standing.all.lose.toString()
        holder.binding.tvGoalsFor.text = standing.all.goals.`for`.toString()
        holder.binding.tvGoalsAgainst.text = standing.all.goals.against.toString()
        holder.binding.tvGoalDifference.text = standing.goalsDiff.toString()
        holder.binding.tvPoints.text = standing.points.toString()

        if (position == standingsList.size - 1) {
            holder.binding.viewSeparator.visibility = View.GONE
        } else {
            holder.binding.viewSeparator.visibility = View.VISIBLE
        }


        Glide.with(holder.itemView.context)
            .load(standing.team.logo)
            .into(holder.binding.ivTeamLogo)
    }

    override fun getItemCount(): Int = standingsList.size

    class RankViewHolder(val binding: ItemStandingBinding) : RecyclerView.ViewHolder(binding.root)
}




