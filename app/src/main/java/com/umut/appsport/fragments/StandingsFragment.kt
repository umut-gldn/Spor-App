package com.umut.appsport.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.umut.appsport.adapter.RankAdapter
import com.umut.appsport.databinding.FragmentStandingsBinding
import com.umut.appsport.model.StandingResponse
import com.umut.appsport.network.APIClient
import com.umut.appsport.network.APIInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StandingsFragment : Fragment() {

    private var _binding: FragmentStandingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var standingsAdapter: RankAdapter
    private var selectedLeague: String? = null
    private var selectedYear: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStandingsBinding.inflate(inflater, container, false)
        val view = binding.root


        binding.rvStandings.layoutManager = LinearLayoutManager(context)
        standingsAdapter = RankAdapter()
        binding.rvStandings.adapter = standingsAdapter


        selectedLeague = arguments?.getString("selectedLeague")
        selectedYear = arguments?.getInt("selectedYear")


        fetchStandings()

        return view
    }

    private fun fetchStandings() {
        val apiInterface = APIClient.retrofit.create(APIInterface::class.java)

        val leagueId = when (selectedLeague) {
            "Premier League" -> "39"
            "La Liga" -> "140"
            "Bundesliga" -> "78"
            "Süper Lig" -> "203"
            else -> 39
        }


        val call = apiInterface.getStandings(leagueId.toString(), selectedYear.toString())

        call.enqueue(object : Callback<StandingResponse> {
            override fun onResponse(call: Call<StandingResponse>, response: Response<StandingResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val standings = response.body()!!.response[0].league.standings[0]
                    standingsAdapter.submitList(standings)
                } else {
                    Log.e("StandingsFragment", "API yanıtı başarısız veya boş!")
                    Toast.makeText(context, "Veriler alınamadı, lütfen tekrar deneyin.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<StandingResponse>, t: Throwable) {
                Log.e("StandingsFragment", "API çağrısı başarısız: ${t.message}")
                Toast.makeText(context, "Bir hata oluştu: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = "Standings"
    }
}


