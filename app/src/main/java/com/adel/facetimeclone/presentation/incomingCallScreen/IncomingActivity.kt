package com.adel.facetimeclone.presentation.incomingCallScreen

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.adel.facetimeclone.databinding.ActivityIncomingBinding
import com.adel.facetimeclone.domain.entities.Result
import com.facebook.react.modules.core.PermissionListener
import dagger.hilt.android.AndroidEntryPoint
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetActivityInterface
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import org.jitsi.meet.sdk.JitsiMeetView

@AndroidEntryPoint
class IncomingActivity : AppCompatActivity(), JitsiMeetActivityInterface {
    var roomKey: String? = null
    var roomType: String = "audio"
    var roomAuthor: String = ""
    var jitsiMeetView: JitsiMeetView? = null

    lateinit var viewModel: IncomingCallViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityIncomingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        viewModel = ViewModelProvider(this).get(IncomingCallViewModel::class.java)
        with(NotificationManagerCompat.from(this)) {
            cancel(1221254588)
        }
        if (intent.hasExtra("response") && intent.hasExtra("name") && intent.hasExtra("key")) {
            roomType = intent.getStringExtra("response")!!
            binding.tvCallType.text = roomType
            binding.tvCallerName.text = intent.getStringExtra("name").toString()
            roomKey = intent.getStringExtra("key")
        } else
            finish()

        binding.btnAgree.setOnClickListener {
            if (roomKey != null)
                viewModel.agreeCall(roomKey!!)
        }
        binding.btnDecline.setOnClickListener {
            if (roomKey != null)
                viewModel.declineCall(roomKey!!)
            finish()
        }
        viewModel.isAgreeSuccess.observe(this, {
            when (it) {
                is Result.Success -> {
                    jitsiMeetView = JitsiMeetView(this)
                    var conferenceOptions: JitsiMeetConferenceOptions =
                            JitsiMeetConferenceOptions.Builder()
                                    .setRoom("https://meet.jit.si/${roomKey!!.replace("-", "1")}")
                                    .setAudioMuted(false)
                                    .setVideoMuted(roomType == "AudioCall")
                                    .setFeatureFlag("invite.enabled", false)
                                    .build()
                    JitsiMeetActivity.launch(this, conferenceOptions)
                }
                is Result.Error ->{
                    Toast.makeText(this,it.msg, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    var invitationResponse: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            val response = p1!!.getStringExtra("response")
            val key = p1.getStringExtra("response")
            if (response == "cancel" && roomKey == key) {
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(applicationContext).registerReceiver(
                invitationResponse,
                IntentFilter("response")
        )
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(
                invitationResponse
        )

    }

    override fun requestPermissions(p0: Array<out String>?, p1: Int, p2: PermissionListener?) {

    }
}