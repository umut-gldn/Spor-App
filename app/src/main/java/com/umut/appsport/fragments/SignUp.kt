package com.umut.appsport.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.umut.appsport.R
import com.umut.appsport.databinding.FragmentSignUpBinding

class SignUp : Fragment() {


    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root
        auth = FirebaseAuth.getInstance()
        val currentUser=auth.currentUser
        if(currentUser!=null){
            findNavController().navigate(R.id.action_signUp_to_home2)

        }
        binding.button.setOnClickListener {
            signUpClicked(view)
        }
        binding.signUpLink.setOnClickListener {
            findNavController().navigate(R.id.action_signUp_to_blank)
        }
        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

     private fun signUpClicked(view: View) {
        val email = binding.emailInput.text.toString()
        val password = binding.passwordInput.text.toString()
        val confirmPassword = binding.passwordInput2.text.toString()

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showError("Please fill in all fields")
    return
        }
        if (password != confirmPassword) {
            showError("Passwords do not match")
    return
        }
        signUp(email, password)
    }

    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    showSuccess("Sign up successful")
                    findNavController().navigate(R.id.action_signUp_to_home2)
                } else {
                    showError(task.exception?.message ?: "Sign up failed")
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_LONG).show()
            }
    }



    private fun showSuccess(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = "Sign Up"
    }
}
