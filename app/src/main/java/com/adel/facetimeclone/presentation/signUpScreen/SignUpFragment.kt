package com.adel.facetimeclone.presentation.signUpScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.adel.facetimeclone.databinding.FragmentSignUpBinding
import com.adel.facetimeclone.presentation.signUpScreen.uiStates.SignupUiEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        lifecycleScope.launchWhenStarted {
            launch(Dispatchers.Main) {
                viewModel.signupUiState.collect {
                    binding.btnSignUp.isEnabled = !it.isLoading
                }
            }
            launch(Dispatchers.Main) {
                viewModel.eventFlow.collectLatest {
                    when (it) {
                        SignupUiEvent.SignupSuccess -> findNavController().navigate(
                            SignUpFragmentDirections.actionSignUpFragmentToHomeFragment()
                        )

                        is SignupUiEvent.ShowMessage -> Toast.makeText(
                            requireContext(),
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        return binding.root
    }
}