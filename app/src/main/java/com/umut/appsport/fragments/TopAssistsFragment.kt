package com.umut.appsport.fragments

import StatsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.umut.appsport.databinding.FragmentTopAssistsBinding
import com.umut.appsport.model.StatsResponse
import com.umut.appsport.network.APIClient
import com.umut.appsport.network.APIInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopAssistsFragment : Fragment() {

    private var _binding: FragmentTopAssistsBinding? = null
    private val binding get() = _binding!!
    private lateinit var topAssistsAdapter: StatsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTopAssistsBinding.inflate(inflater, container, false)
        val view = binding.root

        setupRecyclerView()
        fetchTopAssists()

        return view
    }

    private fun setupRecyclerView() {
        topAssistsAdapter = StatsAdapter(emptyList())
        binding.rvTopAssists.layoutManager = LinearLayoutManager(context)
        binding.rvTopAssists.adapter = topAssistsAdapter
    }

    private fun fetchTopAssists() {
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
        apiInterface.getTopAssists(leagueId.toString(), year.toString()).enqueue(object : Callback<StatsResponse> {
            override fun onResponse(call: Call<StatsResponse>, response: Response<StatsResponse>) {
                if (response.isSuccessful) {
                    val topAssists = response.body()?.response?.take(5) ?: emptyList()
                    topAssistsAdapter.updateData(topAssists)
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
        (activity as AppCompatActivity).supportActionBar?.title = "Top Assists"
    }
}
