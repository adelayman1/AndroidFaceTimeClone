package com.adel.facetimeclone.presentation.homeScreen

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adel.facetimeclone.R
import com.adel.facetimeclone.data.model.RoomModel
import com.adel.facetimeclone.data.utils.DateAndTimeUtils
import com.adel.facetimeclone.databinding.CallItemBinding
import com.adel.facetimeclone.presentation.homeScreen.uiStates.CallItemUiState

class CallsAdapter(var callsList: List<CallItemUiState>) :
    RecyclerView.Adapter<CallsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: CallItemBinding =
            CallItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {

            callsList[position].let { item ->
                if (item.time.isNullOrEmpty())
                    return
                binding.tvDate.text = DateAndTimeUtils.fromMillisSecondToDate(item.time)
                if (callsList.size == 1) { // لو لوحده
                    binding.tvTime.visibility = VISIBLE
                    binding.bottomView.visibility = GONE
                    binding.tvTime.text = DateAndTimeUtils.covertTimeToText(item.time)
                    binding.card.setBackgroundResource(R.drawable.normal_background)
                } else if ((position == 0 && DateAndTimeUtils.covertTimeToText(item.time) == DateAndTimeUtils.covertTimeToText(
                        callsList[position + 1].time ?: "10"
                    ))
                ) {
//               لو الأولاني وتحته عنصر شبه
                    binding.card.setBackgroundResource(R.drawable.top_background)
                    binding.tvTime.visibility = VISIBLE

                    binding.bottomView.visibility = VISIBLE
                    binding.tvTime.text = DateAndTimeUtils.covertTimeToText(item.time)
                } else if ((position == 0 && DateAndTimeUtils.covertTimeToText(item.time) != DateAndTimeUtils.covertTimeToText(
                        callsList[position + 1].time ?: "10"
                    ))
                ) {
                    //               لو الأولاني وتحته عنصر مش شبه
                    binding.card.setBackgroundResource(R.drawable.normal_background)
                    binding.tvTime.visibility = VISIBLE
                    binding.bottomView.visibility = GONE
                    binding.tvTime.text = DateAndTimeUtils.covertTimeToText(item.time)
                } else if ((position != (callsList.size - 1) && DateAndTimeUtils.covertTimeToText(
                        item.time
                    ) == DateAndTimeUtils.covertTimeToText(
                        callsList[position + 1].time ?: "10"
                    ) && DateAndTimeUtils.covertTimeToText(item.time) == DateAndTimeUtils.covertTimeToText(
                        callsList[position - 1].time ?: "10"
                    ))
                ) {
                    //                لو مش الأخير وتحته عنصر شبه وفوقه عنصر شبه
                    binding.card.setBackgroundResource(R.drawable.middle_background)
                    binding.bottomView.visibility = VISIBLE
                    binding.tvTime.visibility = GONE
                } else if ((position == (callsList.size - 1) && DateAndTimeUtils.covertTimeToText(
                        item.time
                    ) == DateAndTimeUtils.covertTimeToText(callsList[position - 1].time ?: "10"))
                ) {
                    //                 لو الأخير وفوقه عنصر شبه
                    binding.card.setBackgroundResource(R.drawable.bottom_background)
                    binding.bottomView.visibility = GONE
                    binding.tvTime.visibility = GONE
                } else if ((position == (callsList.size - 1) && DateAndTimeUtils.covertTimeToText(
                        item.time
                    ) != DateAndTimeUtils.covertTimeToText(callsList[position - 1].time ?: "10"))
                ) {
                    //                 لو الأخير وفوقه عنصر مش شبه
                    binding.card.setBackgroundResource(R.drawable.normal_background)
                    binding.bottomView.visibility = GONE
                    binding.tvTime.visibility = VISIBLE
                    binding.tvTime.text = DateAndTimeUtils.covertTimeToText(item.time)
                } else if ((position != (callsList.size - 1) && DateAndTimeUtils.covertTimeToText(
                        item.time
                    ) != DateAndTimeUtils.covertTimeToText(
                        callsList[position - 1].time ?: "10"
                    ) && DateAndTimeUtils.covertTimeToText(item.time) == DateAndTimeUtils.covertTimeToText(
                        callsList[position + 1].time ?: "10"
                    ))
                ) {
                    //                لو مش الأخير وفوقه عنصر مش شبه وتحته عنصر شبه
                    binding.card.setBackgroundResource(R.drawable.top_background)
                    binding.tvTime.visibility = VISIBLE
                    binding.bottomView.visibility = VISIBLE
                    binding.tvTime.text = DateAndTimeUtils.covertTimeToText(item.time)
                } else if ((position != (callsList.size - 1) && DateAndTimeUtils.covertTimeToText(
                        item.time
                    ) == DateAndTimeUtils.covertTimeToText(
                        callsList[position - 1].time ?: "10"
                    ) && DateAndTimeUtils.covertTimeToText(item.time) != DateAndTimeUtils.covertTimeToText(
                        callsList[position + 1].time ?: "10"
                    ))
                ) {
                    //                لو مش الأخير وفوقه عنصر شبه وتحته عنصر مش شبه
                    binding.card.setBackgroundResource(R.drawable.bottom_background)
                    binding.tvTime.visibility = GONE
                    binding.bottomView.visibility = GONE
                } else if ((position != (callsList.size - 1) && DateAndTimeUtils.covertTimeToText(
                        item.time
                    ) != DateAndTimeUtils.covertTimeToText(
                        callsList[position + 1].time ?: "10"
                    ) && DateAndTimeUtils.covertTimeToText(item.time) != DateAndTimeUtils.covertTimeToText(
                        callsList[position - 1].time ?: "10"
                    ))
                ) {
                    //                لو مش الأخير وتحته عنصر مش شبه وفوقه عنصر مش شبه
                    binding.card.setBackgroundResource(R.drawable.normal_background)
                    binding.bottomView.visibility = GONE
                    binding.tvTime.visibility = VISIBLE
                    binding.tvTime.text = DateAndTimeUtils.covertTimeToText(item.time)
                }
                if (item.roomType == "link") {
                    binding.tvName.text = "FaceTime Link"
                    binding.tvCallType.text = "FaceTime"
                    binding.ivCallType.setImageResource(R.drawable.ic_round_link_24)
                } else {
                    binding.tvName.text = item.roomAuthor
                    if (item.roomType == "AudioCall") {
                        binding.ivCallType.setImageResource(R.drawable.ic_baseline_call_24)
                        binding.tvCallType.text = "FaceTime Audio"
                    } else if (item.roomType == "FaceTime") {
                        binding.ivCallType.setImageResource(R.drawable.ic_round_videocam_24)
                        binding.tvCallType.text = "FaceTime"
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = callsList.size

    inner class ViewHolder(val binding: CallItemBinding) : RecyclerView.ViewHolder(binding.root)
}