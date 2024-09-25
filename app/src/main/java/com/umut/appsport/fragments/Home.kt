package com.umut.appsport.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.umut.appsport.MainActivity
import com.umut.appsport.R
import com.umut.appsport.databinding.FragmentHomeBinding

class Home : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        setupSpinners()
        setupButtonListeners()

        return view
    }

    private fun setupSpinners() {
        val leagues = arrayOf("Premier League", "La Liga", "Bundesliga", "Süper Lig")
        val years = arrayOf("2023", "2022", "2021", "2020", "2019")

        val leagueAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, leagues)
        leagueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerLeague.adapter = leagueAdapter

        val yearAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, years)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerYear.adapter = yearAdapter
    }

    private fun setupButtonListeners() {
        binding.btnContinue.setOnClickListener {
            handleContinueButton()
        }
        binding.btnViewTopScorers.setOnClickListener {
            handleViewTopScorersButton()
        }
        binding.btnViewTopAssists.setOnClickListener {
            handleViewTopAssistsButton()
        }
        binding.btnViewStandings.setOnClickListener {
            handleViewStandingsButton()
        }
    }

    private fun handleContinueButton() {
        val selectedLeague = binding.spinnerLeague.selectedItem.toString()
        val selectedYear = binding.spinnerYear.selectedItem.toString().toInt()

        (activity as MainActivity).updateSelectedLeague(selectedLeague)
        (activity as MainActivity).updateSelectedYear(selectedYear)

        val bundle = Bundle().apply {
            putString("selectedLeague", selectedLeague)
            putInt("selectedYear", selectedYear)
        }

        findNavController().navigate(R.id.action_home2_to_fixtureFragment, bundle)
    }

    private fun handleViewTopScorersButton() {
        val selectedLeague = binding.spinnerLeague.selectedItem.toString()
        val selectedYear = binding.spinnerYear.selectedItem.toString().toInt()

        (activity as MainActivity).updateSelectedLeague(selectedLeague)
        (activity as MainActivity).updateSelectedYear(selectedYear)

        val bundle = Bundle().apply {
            putString("selectedLeague", selectedLeague)
            putInt("selectedYear", selectedYear)
        }

        findNavController().navigate(R.id.action_home2_to_topScorersFragment, bundle)
    }

    private fun handleViewTopAssistsButton() {
        val selectedLeague = binding.spinnerLeague.selectedItem.toString()
        val selectedYear = binding.spinnerYear.selectedItem.toString().toInt()

        (activity as MainActivity).updateSelectedLeague(selectedLeague)
        (activity as MainActivity).updateSelectedYear(selectedYear)

        val bundle = Bundle().apply {
            putString("selectedLeague", selectedLeague)
            putInt("selectedYear", selectedYear)
        }

        findNavController().navigate(R.id.action_home2_to_topAssistsFragment, bundle)
    }
    private fun handleViewStandingsButton() {
        val selectedLeague = binding.spinnerLeague.selectedItem.toString()
        val selectedYear = binding.spinnerYear.selectedItem.toString().toInt()

        (activity as MainActivity).updateSelectedLeague(selectedLeague)
        (activity as MainActivity).updateSelectedYear(selectedYear)

        val bundle = Bundle().apply {
            putString("selectedLeague", selectedLeague)
            putInt("selectedYear", selectedYear)
        }
        findNavController().navigate(R.id.action_home2_to_standingsFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = "Home"
    }
}
