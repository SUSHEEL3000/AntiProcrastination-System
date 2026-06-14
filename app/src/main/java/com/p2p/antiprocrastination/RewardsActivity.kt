package com.p2p.antiprocrastination

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.p2p.antiprocrastination.databinding.ActivityRewardsBinding
import com.p2p.antiprocrastination.utils.PreferenceManager

class RewardsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRewardsBinding

    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            ActivityRewardsBinding.inflate(
                layoutInflater
            )

        setContentView(binding.root)

        preferenceManager =
            PreferenceManager(this)

        val xp =
            preferenceManager.getXp()

        binding.tvXpValue.text =
            xp.toString()

        loadAchievements(xp)
    }

    private fun loadAchievements(
        xp: Int
    ) {

        val achievements =
            buildString {

                appendLine(
                    if (xp >= 50)
                        "✓ Beginner (50 XP)"
                    else
                        "🔒 Beginner (50 XP)"
                )

                appendLine(
                    if (xp >= 100)
                        "✓ Consistent (100 XP)"
                    else
                        "🔒 Consistent (100 XP)"
                )

                appendLine(
                    if (xp >= 250)
                        "✓ Focused (250 XP)"
                    else
                        "🔒 Focused (250 XP)"
                )

                appendLine(
                    if (xp >= 500)
                        "✓ Disciplined (500 XP)"
                    else
                        "🔒 Disciplined (500 XP)"
                )

                appendLine(
                    if (xp >= 1000)
                        "✓ Unstoppable (1000 XP)"
                    else
                        "🔒 Unstoppable (1000 XP)"
                )
            }

        binding.tvAchievements.text =
            achievements
    }
}