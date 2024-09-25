package com.umut.appsport.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.umut.appsport.R
import com.umut.appsport.adapter.FixtureAdapter
import com.umut.appsport.databinding.FragmentFixtureBinding
import com.umut.appsport.model.Fixture
import com.umut.appsport.model.FixtureResponse
import com.umut.appsport.model.RoundsResponse
import com.umut.appsport.network.APIClient
import com.umut.appsport.network.APIInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FixtureFragment : Fragment() {
    private var _binding: FragmentFixtureBinding? = null
    private val binding get() = _binding!!
    private lateinit var fixtureAdapter: FixtureAdapter
    private var fixtureList = mutableListOf<Fixture>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFixtureBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.rvFixtures.layoutManager = LinearLayoutManager(context)

        val selectedLeague = arguments?.getString("selectedLeague")
        val selectedYear = arguments?.getInt("selectedYear")

        Log.d("FixtureFragment", "Selected League: $selectedLeague")
        Log.d("FixtureFragment", "Selected Year: $selectedYear")

        selectedLeague?.let { league ->
            selectedYear?.let { year ->
                fetchRounds(league, year)
            }
        }

        return view
    }

    private fun fetchRounds(league: String, year: Int) {
        val leagueId = when (league) {
            "Premier League" -> 39
            "La Liga" -> 140
            "Bundesliga" -> 78
            "Süper Lig" -> 203
            else -> 39
        }

        val apiInterface = APIClient.retrofit.create(APIInterface::class.java)
        apiInterface.getRounds(leagueId.toString(), year.toString()).enqueue(object : Callback<RoundsResponse> {
            override fun onResponse(
                call: Call<RoundsResponse>,
                response: Response<RoundsResponse>
            ) {
                if(response.isSuccessful) {
                    val rounds = response.body()?.response ?: emptyList()
                    setupRoundsSpinner(rounds)
                } else {
                    Log.d("FixtureFragment", "Response failed with code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<RoundsResponse>, t: Throwable) {
                t.printStackTrace()
                Log.e("FixtureFragment", "API Failure: ${t.message}")
            }
        })
    }

    private fun setupRoundsSpinner(rounds: List<String>) {
        val roundsAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, rounds)
        roundsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerRounds.adapter = roundsAdapter

        binding.spinnerRounds.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedRound = rounds[position]
                fetchFixtures(selectedRound)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun fetchFixtures(round: String) {
        val league = arguments?.getString("selectedLeague")
        val year = arguments?.getInt("selectedYear")
        val leagueId = when (league) {
            "Premier League" -> 39
            "La Liga" -> 140
            "Bundesliga" -> 78
            "Süper Lig" -> 203
            else -> 39
        }

        val apiInterface = APIClient.retrofit.create(APIInterface::class.java)
        apiInterface.getFixturesByRound(leagueId.toString(), year.toString(), round).enqueue(object : Callback<FixtureResponse> {
            override fun onResponse(
                call: Call<FixtureResponse>,
                response: Response<FixtureResponse>
            ) {
                if(response.isSuccessful) {
                    val fixtures = response.body()?.response ?: emptyList()
                    fixtureAdapter = FixtureAdapter(fixtures)
                    binding.rvFixtures.adapter = fixtureAdapter
                    fixtureAdapter.notifyDataSetChanged()
                    Log.d("FixtureFragment", "Number of fixtures: ${fixtures.size}")
                } else {
                    Log.d("FixtureFragment", "Response failed with code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<FixtureResponse>, t: Throwable) {
                t.printStackTrace()
                Log.e("FixtureFragment", "API Failure: ${t.message}")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = "Fixtures"
    }
}
