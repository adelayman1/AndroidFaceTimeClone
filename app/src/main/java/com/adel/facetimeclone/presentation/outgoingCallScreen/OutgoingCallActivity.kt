package com.adel.facetimeclone.presentation.outgoingCallScreen

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.adel.facetimeclone.R
import com.adel.facetimeclone.presentation.outgoingCallScreen.uiStates.OutgoingCallUiEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import org.jitsi.meet.sdk.BroadcastEvent
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import java.net.URL

@AndroidEntryPoint
class OutgoingCallActivity : JitsiMeetActivity() {
    var usersInvitedList: ArrayList<String> = arrayListOf()
    lateinit var viewModel: OutgoingCallViewModel
    var roomKey = ""
    var roomType = "AudioCall"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outgoing_call)
        viewModel = ViewModelProvider(this).get(OutgoingCallViewModel::class.java)
        if (intent.hasExtra("usersInvited") && intent.hasExtra("roomKey")) {
            usersInvitedList.clear()
            usersInvitedList.addAll(intent.getStringArrayListExtra("usersInvited")!!)
            roomKey = intent.getStringExtra("roomKey")!!
            roomType = intent.getStringExtra("roomType")!!
        }
        val conferenceOptions: JitsiMeetConferenceOptions =
                JitsiMeetConferenceOptions.Builder()
                        .setServerURL(URL("https://meet.jit.si"))
                        .setRoom("https://meet.jit.si/${roomKey.replace("-", "1")}")
                        .setAudioMuted(false)
                        .setVideoMuted(roomType == "AudioCall")
                        .setFeatureFlag("invite.enabled", false)
                        .build()
        join(conferenceOptions)
        launch(this, conferenceOptions)
        LocalBroadcastManager.getInstance(this).registerReceiver(
                conferenceTerminatedListener,
                IntentFilter(BroadcastEvent.Type.CONFERENCE_TERMINATED.action)
        )
        lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collect {
                when(it){
                    OutgoingCallUiEvent.ClosedSuccess -> finish()
                    is OutgoingCallUiEvent.ShowMessage -> Toast.makeText(this@OutgoingCallActivity, it.message, Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
    var conferenceTerminatedListener: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            if (usersInvitedList.size != 0 && roomKey.trim().isNotEmpty()) {
                viewModel.closeRoom(roomKey, usersInvitedList)
            }
        }
    }

}