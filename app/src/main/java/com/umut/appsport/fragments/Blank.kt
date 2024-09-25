package com.umut.appsport.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.umut.appsport.R
import com.umut.appsport.databinding.FragmentBlankBinding
import com.umut.appsport.databinding.FragmentSignUpBinding

class Blank : Fragment() {


    private lateinit var auth:FirebaseAuth
    private var _binding: FragmentBlankBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBlankBinding.inflate(inflater, container, false)
        val view = binding.root
        auth = FirebaseAuth.getInstance()
        binding.button.setOnClickListener {
            login(view)

        }
        binding.signUpLink.setOnClickListener {
            findNavController().navigate(R.id.action_blank_to_signUp)
        }
        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun login(view:View)
    {
        val email=binding.emailInput.text.toString()
        val password=binding.passwordInput.text.toString()
        if(email.isEmpty()||password.isEmpty())
        {
            showError("Please fill in all fields")
            return
        }
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful) {
                showSuccess("Login successful")
                findNavController().navigate(R.id.action_blank_to_home2)
            } else {
                showError(task.exception?.message ?: "Login failed")
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_LONG).show()
        }

    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    private fun showSuccess(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = "Login"
    }
    }
