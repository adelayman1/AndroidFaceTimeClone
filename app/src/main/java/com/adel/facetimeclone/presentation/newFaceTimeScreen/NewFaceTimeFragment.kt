package com.adel.facetimeclone.presentation.newFaceTimeScreen

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.adel.facetimeclone.R
import com.adel.facetimeclone.databinding.FragmentNewFaceTimeBinding
import com.adel.facetimeclone.domain.entities.Result
import com.adel.facetimeclone.presentation.outgoingCallScreen.OutgoingCallActivity
import com.google.android.material.chip.Chip
import java.util.*
import kotlin.collections.ArrayList

class NewFaceTimeFragment : Fragment() {
    lateinit var viewModel: NewFaceTimeViewModel
    var roomType = "AudioCall"
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewFaceTimeBinding.inflate(inflater, container, false)
        requireActivity().window.statusBarColor = Color.parseColor("#1c1c1d")
        viewModel = ViewModelProvider(requireActivity()).get(NewFaceTimeViewModel::class.java)
        binding.etToUsers.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val txtVal = v.text.toString()
                if (txtVal.trim().isNotEmpty() && txtVal.length >= 5 && binding.chipGroup.childCount < 50) {
                    if (viewModel.isUserDownloadedApp.value is Result.Null) {
                        for (i in 0 until binding.chipGroup.childCount) {
                            val chipObj = binding.chipGroup.getChildAt(i) as Chip
                            if (chipObj.text.trim().toString() == binding.etToUsers.text!!.trim().toString()) {
                                binding.etToUsers.setText("")
                                Toast.makeText(requireContext(), "You have added this user", Toast.LENGTH_LONG).show()
                                return@OnEditorActionListener false
                            }
                        }
                        viewModel.checkIsUserDownloadedTheApp(txtVal.trim())
                        binding.etToUsers.setText("")
                    } else {
                        Toast.makeText(requireContext(), "Please wait before try again...", Toast.LENGTH_SHORT).show()
                    }
                }

                return@OnEditorActionListener true
            }
            false
        })
        binding.chipGroup.addOnLayoutChangeListener { view, i, i2, i3, i4, i5, i6, i7, i8 ->
            if (binding.chipGroup.childCount == 0) {
                binding.btnFaceTimeCall.background.setTint(Color.parseColor("#323235"))
                binding.btnAudioCall.background.setTint(Color.parseColor("#323235"))
                binding.ivAudioCall.imageTintList = ColorStateList.valueOf(Color.parseColor("#69696f"))
                binding.ivFaceTimeCall.imageTintList = ColorStateList.valueOf(Color.parseColor("#69696f"))
                binding.tvFaceTime.setTextColor(Color.parseColor("#69696f"))
            } else {
                binding.btnFaceTimeCall.background.setTint(Color.parseColor("#2ed158"))
                binding.btnAudioCall.background.setTint(Color.parseColor("#21492c"))
                binding.ivAudioCall.imageTintList = ColorStateList.valueOf(Color.parseColor("#35c759"))
                binding.ivFaceTimeCall.imageTintList = ColorStateList.valueOf(Color.parseColor("#ffffff"))
                binding.tvFaceTime.setTextColor(Color.parseColor("#ffffff"))
            }
        }
        binding.btnFaceTimeCall.setOnClickListener {
            val list: MutableList<String> = mutableListOf()
            for (i in 0 until binding.chipGroup.childCount) {
                val chipObj = binding.chipGroup.getChildAt(i) as Chip
                list.add(chipObj.text.toString())
            }
            roomType = "FaceTime"
            viewModel.call(list, "FaceTime")
        }
        binding.btnAudioCall.setOnClickListener {
            val list: MutableList<String> = mutableListOf()
            for (i in 0 until binding.chipGroup.childCount) {
                val chipObj = binding.chipGroup.getChildAt(i) as Chip
                list.add(chipObj.text.toString())
            }
            roomType = "AudioCall"
            viewModel.call(list, "AudioCall")
        }
        viewModel.isUserDownloadedApp.observe(requireActivity(), Observer {
            when (it) {
                is Result.Success -> {
                    if (it.data.second) {
                        val chip = Chip(requireContext())
                        chip.text = it.data.first.trim()
                        chip.chipIcon = ContextCompat.getDrawable(requireContext(), R.mipmap.ic_launcher_round)
                        chip.isCloseIconVisible = true
                        chip.isClickable = true
                        chip.isCheckable = false
                        binding.chipGroup.addView(chip as View, binding.chipGroup.childCount)
                        chip.setOnCloseIconClickListener { binding.chipGroup.removeView(chip as View) }
                    } else {
                        Toast.makeText(requireContext(), "User you have inserted didn't download this app", Toast.LENGTH_LONG).show()
                    }
                    viewModel.isUserDownloadedApp.value = Result.Null()
                }
                is Result.Error ->{
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.isCreatedCallSuccess.observe(requireActivity(), {
            when (it) {
                is Result.Success -> {
                    val intent: Intent = Intent(requireContext(), OutgoingCallActivity::class.java)
                    intent.putExtra("roomKey", it.data.first)
                    intent.putExtra("usersInvited", ArrayList(it.data.second))
                    intent.putExtra("roomType", roomType)
                    startActivity(intent)
                }
                is Result.Error ->{
                    Toast.makeText(requireContext(),it.msg,Toast.LENGTH_SHORT).show()
                }
            }
        })
        binding.tvCancel.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }
}