package com.p2p.antiprocrastination

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.p2p.antiprocrastination.databinding.ActivityReversePomodoroBinding
import com.p2p.antiprocrastination.utils.PreferenceManager

class ReversePomodoroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReversePomodoroBinding

    private lateinit var preferenceManager: PreferenceManager

    private var sessionCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityReversePomodoroBinding.inflate(
                layoutInflater
            )

        setContentView(binding.root)

        preferenceManager =
            PreferenceManager(this)

        binding.btnStartPomodoro.setOnClickListener {
            startWorkSession()
        }
    }

    private fun startWorkSession() {

        binding.tvStatus.text = "Focus Session"

        binding.btnStartPomodoro.isEnabled = false

        object : CountDownTimer(
            5 * 60 * 1000L,
            1000
        ) {

            override fun onTick(
                millisUntilFinished: Long
            ) {

                binding.tvTimer.text =
                    "${millisUntilFinished / 1000}s"
            }

            override fun onFinish() {

                startBreakSession()
            }

        }.start()
    }

    private fun startBreakSession() {

        binding.tvStatus.text = "Break Time"

        object : CountDownTimer(
            60 * 1000L,
            1000
        ) {

            override fun onTick(
                millisUntilFinished: Long
            ) {

                binding.tvTimer.text =
                    "${millisUntilFinished / 1000}s"
            }

            override fun onFinish() {

                sessionCount++

                binding.tvSessions.text =
                    "Sessions: $sessionCount"

                preferenceManager.addXp(25)

                binding.tvStatus.text =
                    "Session Complete"

                binding.tvTimer.text =
                    "Done"

                binding.btnStartPomodoro.isEnabled = true
            }

        }.start()
    }
}

