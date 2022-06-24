package com.adel.facetimeclone.presentation.signUpScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.adel.facetimeclone.databinding.FragmentSignUpBinding
import com.adel.facetimeclone.domain.entities.Result

lateinit var viewModel: SignUpViewModel
class SignUpFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentSignUpBinding.inflate(inflater, container, false)
        viewModel= ViewModelProvider(requireActivity()).get(SignUpViewModel::class.java)
        binding.btnSignUp.setOnClickListener {
            viewModel.signUp(binding.etEmail.text.toString(),binding.etPassword.text.toString(),binding.etName.text.toString())
        }
        binding.tvHaveAnAccount.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
        }
        viewModel.isLoading.observe(this,{
            binding.btnSignUp.isEnabled = !it
        })
        viewModel.isSignedUpSuccess.observe(this,{
            when(it){
                is Result.Success ->{
                    findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToHomeFragment())
                }
                is Result.Error ->{
                    Toast.makeText(requireContext(),it.msg, Toast.LENGTH_SHORT).show()
                }
            }
        })
        return binding.root
    }
}