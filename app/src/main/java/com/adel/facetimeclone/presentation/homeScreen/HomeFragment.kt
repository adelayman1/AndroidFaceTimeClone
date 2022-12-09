package com.adel.facetimeclone.presentation.homeScreen

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.adel.facetimeclone.R
import com.adel.facetimeclone.data.model.RoomModel
import com.adel.facetimeclone.databinding.FragmentHomeBinding
import com.adel.facetimeclone.databinding.FragmentLoginBinding
import com.adel.facetimeclone.domain.entities.Result
import com.adel.facetimeclone.presentation.homeScreen.uiStates.CallItemUiState
import com.adel.facetimeclone.presentation.homeScreen.uiStates.HomeUiEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File


class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private var callsList: MutableList<CallItemUiState> = mutableListOf()
    private lateinit var adapter: CallsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.background)

        binding.lnCreateLink.setOnClickListener {
            viewModel.createRoomLink()
        }
        lifecycleScope.launchWhenStarted {
            launch(Dispatchers.Main) {
                viewModel.homeUiState.collect {
                    binding.progressHome.visibility = if (it.isLoading) View.VISIBLE else View.GONE
                    if (it.callsList.isNotEmpty()) {
                        callsList.clear()
                        callsList.addAll(it.callsList)
                        adapter.notifyDataSetChanged()
                    }
                    if (it.isLinkDialogVisible) {
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(
                                Intent.EXTRA_TEXT,
                                "Join to my FaceTime now! https://adelayman1ljlhkjb.github.io/faceTime.github.io/FaceTime.html?room=${it.roomLink}"
                            )
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        startActivity(shareIntent)
                        viewModel.hideShareDialog()
                    }
                }
            }
            launch(Dispatchers.Main) {
                viewModel.eventFlow.collect {
                    when (it) {
                        is HomeUiEvent.ShowMessage -> Toast.makeText(
                            requireContext(),
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        binding.lnCreateFaceTime.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNewFaceTimeFragment())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        adapter = CallsAdapter(callsList)
        binding.rcCalls.adapter = adapter
        binding.rcCalls.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        return binding.root
    }

}