package com.p2p.antiprocrastination

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.p2p.antiprocrastination.databinding.ActivityMainBinding
import com.p2p.antiprocrastination.utils.PreferenceManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(this)

        initializeDashboard()
    }

    private fun initializeDashboard() {

        updateDashboardStats()

        binding.btnChain.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ChainActivity::class.java
                )
            )
        }

        binding.btnInstantAction.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    InstantActionActivity::class.java
                )
            )
        }

        binding.btnOneMinuteRule.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    OneMinuteRuleActivity::class.java
                )
            )
        }

        binding.btnReversePomodoro.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ReversePomodoroActivity::class.java
                )
            )
        }

        binding.btnRewards.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    RewardsActivity::class.java
                )
            )
        }
    }

    private fun updateDashboardStats() {

        binding.tvXp.text =
            preferenceManager
                .getXp()
                .toString()

        binding.tvStreak.text =
            "${preferenceManager.getCurrentStreak()} Days"

        binding.tvLongestStreak.text =
            "${preferenceManager.getLongestStreak()} Days"

        binding.tvFreezeDays.text =
            preferenceManager
                .getFreezeDays()
                .toString()
    }

    override fun onResume() {
        super.onResume()

        updateDashboardStats()
    }
}