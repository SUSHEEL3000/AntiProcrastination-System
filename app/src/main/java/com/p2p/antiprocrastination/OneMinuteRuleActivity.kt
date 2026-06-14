package com.p2p.antiprocrastination

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.p2p.antiprocrastination.databinding.ActivityOneMinuteRuleBinding
import com.p2p.antiprocrastination.utils.PreferenceManager

class OneMinuteRuleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOneMinuteRuleBinding

    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityOneMinuteRuleBinding.inflate(
                layoutInflater
            )

        setContentView(binding.root)

        preferenceManager =
            PreferenceManager(this)

        binding.btnStartOneMinute.setOnClickListener {
            startOneMinuteTimer()
        }
    }

    private fun startOneMinuteTimer() {

        binding.btnStartOneMinute.isEnabled = false

        object : CountDownTimer(
            60000,
            1000
        ) {

            override fun onTick(
                millisUntilFinished: Long
            ) {

                binding.tvTimer.text =
                    "${millisUntilFinished / 1000}s"
            }

            override fun onFinish() {

                binding.tvTimer.text =
                    "Done"

                binding.tvMessage.text =
                    "Momentum Created"

                preferenceManager.addXp(15)
            }
        }.start()
    }
}