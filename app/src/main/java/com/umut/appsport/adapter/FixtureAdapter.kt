package com.umut.appsport.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umut.appsport.databinding.ItemFixtureBinding
import com.umut.appsport.model.Fixture
import java.text.SimpleDateFormat
import java.util.*

class FixtureAdapter(private var fixtures: List<Fixture>) :
    RecyclerView.Adapter<FixtureAdapter.FixtureViewHolder>() {


    private var expandedPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FixtureViewHolder {
        val binding = ItemFixtureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FixtureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FixtureViewHolder, position: Int) {
        val fixture = fixtures[position]
        holder.bind(fixture)


        val isExpanded = position == expandedPosition
        holder.binding.expandedLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE


        holder.itemView.setOnClickListener {
            expandedPosition = if (isExpanded) {
                RecyclerView.NO_POSITION
            } else {
                position
            }
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return fixtures.size
    }

    fun updateFixtures(newFixtures: List<Fixture>) {
        fixtures = newFixtures
        notifyDataSetChanged()
    }

    class FixtureViewHolder(val binding: ItemFixtureBinding) : RecyclerView.ViewHolder(binding.root) {
        private val score: String = "0"
        private val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

        fun bind(fixture: Fixture) {

            binding.tvHomeTeam.text = fixture.teams.home.name
            binding.tvAwayTeam.text = fixture.teams.away.name
            binding.tvDate.text = dateFormat.format(Date(fixture.fixture.timestamp * 1000))
            binding.tvScore.text = "${fixture.goals.home ?: score} - ${fixture.goals.away ?: score}"

            Glide.with(binding.ivHomeLogo.context).load(fixture.teams.home.logo).into(binding.ivHomeLogo)
            Glide.with(binding.ivAwayLogo.context).load(fixture.teams.away.logo).into(binding.ivAwayLogo)


            binding.tvRefereeName.text = fixture.fixture.referee ?: "Unknown"
            binding.tvHalftimeHomeScore.text = fixture.score.halftime?.home.toString()
            binding.tvHalftimeAwayScore.text = fixture.score.halftime?.away.toString()
        }
    }
}
