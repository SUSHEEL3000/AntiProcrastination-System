package com.p2p.antiprocrastination

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.p2p.antiprocrastination.databinding.ActivityInstantActionBinding
import com.p2p.antiprocrastination.utils.PreferenceManager

class InstantActionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInstantActionBinding

    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityInstantActionBinding.inflate(
                layoutInflater
            )

        setContentView(binding.root)

        preferenceManager =
            PreferenceManager(this)

        binding.btnStart.setOnClickListener {

            startCountdown()
        }
    }

    private fun startCountdown() {

        object : CountDownTimer(
            5000,
            1000
        ) {

            override fun onTick(
                millisUntilFinished: Long
            ) {

                binding.tvCountdown.text =
                    (millisUntilFinished / 1000)
                        .toString()
            }

            override fun onFinish() {

                binding.tvCountdown.text =
                    "START NOW!"

                binding.tvMessage.text =
                    "Action beats motivation."

                preferenceManager.addXp(5)
            }
        }.start()
    }
}