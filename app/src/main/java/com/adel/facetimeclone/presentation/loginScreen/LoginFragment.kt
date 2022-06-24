package com.adel.facetimeclone.presentation.loginScreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.adel.facetimeclone.databinding.FragmentLoginBinding
import com.adel.facetimeclone.domain.entities.Result
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    lateinit var viewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel=ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        binding.btnSignIn.setOnClickListener {
            viewModel.login(binding.etEmail.text.toString(),binding.etPassword.text.toString())
        }
        binding.tvCreateNewAccount.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
        }
        viewModel.isLoading.observe(this,{
            binding.btnSignIn.isEnabled = !it
        })
        viewModel.isLoggingSuccess.observe(this,{
            when(it){
                is Result.Success ->{
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                }
                is Result.Error ->{
                    Toast.makeText(requireContext(),it.msg, Toast.LENGTH_SHORT).show()
                }
            }
        })
        return binding.root
    }
}