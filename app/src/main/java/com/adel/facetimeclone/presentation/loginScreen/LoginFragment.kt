package com.adel.facetimeclone.presentation.loginScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.adel.facetimeclone.databinding.FragmentLoginBinding
import com.adel.facetimeclone.presentation.loginScreen.uiStates.LoginUiEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    lateinit var viewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        binding.btnSignIn.setOnClickListener {
            viewModel.login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }
        binding.tvCreateNewAccount.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
        }
        lifecycleScope.launchWhenStarted {
            launch(Dispatchers.Main){
                viewModel.loginUiState.collect {
                    binding.btnSignIn.isEnabled = !it.isLoading
                }
            }
            launch(Dispatchers.Main){
                viewModel.eventFlow.collectLatest {
                    when (it) {
                        LoginUiEvent.LoginSuccess -> findNavController().navigate(
                            LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                        )

                        is LoginUiEvent.ShowMessage -> Toast.makeText(
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