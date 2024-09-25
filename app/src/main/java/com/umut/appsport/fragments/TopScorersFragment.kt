package com.umut.appsport.fragments

import StatsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.umut.appsport.databinding.FragmentTopScorersBinding
import com.umut.appsport.model.StatsResponse
import com.umut.appsport.network.APIClient
import com.umut.appsport.network.APIInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopScorersFragment : Fragment() {

    private var _binding: FragmentTopScorersBinding? = null
    private val binding get() = _binding!!
    private lateinit var topScorersAdapter: StatsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTopScorersBinding.inflate(inflater, container, false)
        val view = binding.root

        setupRecyclerView()
        fetchTopScorers()

        return view
    }

    private fun setupRecyclerView() {
        topScorersAdapter = StatsAdapter(emptyList())
        binding.rvTopScorers.layoutManager = LinearLayoutManager(context)
        binding.rvTopScorers.adapter = topScorersAdapter
    }

    private fun fetchTopScorers() {
        val league = arguments?.getString("selectedLeague") ?: "Premier League"
        val year = arguments?.getInt("selectedYear") ?: 2023
        val leagueId = when (league) {
            "Premier League" -> 39
            "La Liga" -> 140
            "Bundesliga" -> 78
            "Süper Lig" -> 203
            else -> 39
        }

        val apiInterface = APIClient.retrofit.create(APIInterface::class.java)
        apiInterface.getTopScorers(leagueId.toString(), year.toString()).enqueue(object : Callback<StatsResponse> {
            override fun onResponse(call: Call<StatsResponse>, response: Response<StatsResponse>) {
                if (response.isSuccessful) {
                    val topScorers = response.body()?.response?.take(5) ?: emptyList()
                    topScorersAdapter.updateData(topScorers)
                }
            }

            override fun onFailure(call: Call<StatsResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = "Top Scorers"
    }
}
