import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umut.appsport.databinding.ItemStatsBinding
import com.umut.appsport.model.PlayerStats

class StatsAdapter(private var playerStats: List<PlayerStats>) :
    RecyclerView.Adapter<StatsAdapter.StatsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsViewHolder {
        val binding = ItemStatsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StatsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StatsViewHolder, position: Int) {
        holder.bind(playerStats[position])
    }

    override fun getItemCount(): Int {
        return playerStats.size
    }

    fun updateData(newStats: List<PlayerStats>) {
        playerStats = newStats
        notifyDataSetChanged()
    }

    class StatsViewHolder(private val binding: ItemStatsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(playerStats: PlayerStats) {
            binding.tvPlayerName.text = playerStats.player.name
            binding.tvTeamName.text = playerStats.statistics[0].team.name
            binding.tvGoals.text = playerStats.statistics[0].goals?.total?.toString() ?: "0"
            binding.tvAssists.text = playerStats.statistics[0].goals?.assists?.toString() ?: "0"
            Glide.with(binding.ivPlayerPhoto.context)
                .load(playerStats.player.photo)
                .into(binding.ivPlayerPhoto)
        }
    }
}
