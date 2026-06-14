package com.p2p.antiprocrastination

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.p2p.antiprocrastination.databinding.ActivityChainBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.p2p.antiprocrastination.adapter.TaskAdapter
import com.p2p.antiprocrastination.model.Task
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputEditText
import com.p2p.antiprocrastination.utils.PreferenceManager
import com.p2p.antiprocrastination.utils.StreakUtils
class ChainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChainBinding
    private val taskList = mutableListOf<Task>()
    private val maxTasks = 3
    private lateinit var taskAdapter: TaskAdapter

    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferenceManager = PreferenceManager(this)
        setupUI()
    }

    private fun setupUI() {

        taskList.clear()
        taskList.addAll(
            preferenceManager.getTasks()
        )

        checkForDailyReset()

        taskList.clear()
        taskList.addAll(
            preferenceManager.getTasks()
        )

        validateStreak()

        binding.tvCurrentStreak.text =
            "Current Streak: ${
                preferenceManager.getCurrentStreak()
            } Days"

        updateTasksRemaining()
        updateFreezeDisplay()

        taskAdapter = TaskAdapter(

            taskList,

            { task, completed ->

                if (completed) {

                    updateStreak()

                    if (!task.xpAwarded) {

                        preferenceManager.addXp(
                            task.xpReward
                        )

                        task.xpAwarded = true
                    }

                    binding.tvCurrentStreak.text =
                        "Current Streak: ${
                            preferenceManager.getCurrentStreak()
                        } Days"
                }

                saveTasks()

                refreshTaskStats()
            },

            { position ->

                taskList.removeAt(position)

                taskAdapter.notifyItemRemoved(position)

                saveTasks()

                refreshTaskStats()
            }
        )

        binding.rvTasks.layoutManager =
            LinearLayoutManager(this)

        binding.rvTasks.adapter =
            taskAdapter

        taskAdapter.notifyDataSetChanged()

        refreshTaskStats()

        binding.btnAddTask.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun showAddTaskDialog() {

        if (taskList.size >= maxTasks) {

            AlertDialog.Builder(this)
                .setTitle("Limit Reached")
                .setMessage("You can only have 3 tasks per day.")
                .setPositiveButton("OK", null)
                .show()

            return
        }

        val view =
            layoutInflater.inflate(
                R.layout.dialog_add_task,
                null
            )

        val etTask =
            view.findViewById<TextInputEditText>(
                R.id.etTaskName
            )

        AlertDialog.Builder(this)
            .setTitle("Add Task")
            .setView(view)
            .setPositiveButton("Add") { _, _ ->

                val taskName =
                    etTask.text.toString().trim()

                if (taskName.isNotEmpty()) {

                    taskList.add(
                        Task(
                            id = System.currentTimeMillis(),
                            title = taskName
                        )
                    )

                    taskAdapter.notifyItemInserted(
                        taskList.lastIndex
                    )
                    saveTasks()
                    updateTasksRemaining()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()


    }
    private fun updateTasksRemaining() {

        val remaining =
            taskList.count { !it.isCompleted }

        binding.tvTasksRemaining.text =
            "Tasks Remaining: $remaining"
    }
    private fun saveTasks() {

        preferenceManager.saveTasks(taskList)
    }
    private fun checkForDailyReset() {

        val currentTime =
            System.currentTimeMillis()

        val lastReset =
            preferenceManager
                .getLastResetDate()

        if (lastReset == 0L) {

            preferenceManager
                .saveLastResetDate(
                    currentTime
                )

            return
        }

        if (!StreakUtils.isSameDay(
                lastReset,
                currentTime
            )
       )
       {

            taskList.clear()
            saveTasks()

            preferenceManager
                .saveLastResetDate(
                    currentTime
                )
        }
    }
    private fun updateStreak() {

        val currentTime =
            System.currentTimeMillis()

        val lastCompletionDate =
            preferenceManager.getLastCompletionDate()

        val currentStreak =
            preferenceManager.getCurrentStreak()

        if (currentStreak == 0) {

            preferenceManager.saveCurrentStreak(1)

            preferenceManager.saveLastCompletionDate(
                currentTime
            )

            return
        }

        if (lastCompletionDate == 0L) {

            preferenceManager.saveCurrentStreak(1)

        } else {

            when {

                StreakUtils.isSameDay(
                    lastCompletionDate,
                    currentTime
                ) -> {

                    // already counted today
                    return
                }

                StreakUtils.isYesterday(
                    lastCompletionDate,
                    currentTime
                ) -> {

                    val streak =
                        currentStreak + 1

                    preferenceManager.saveCurrentStreak(
                        streak
                    )

                    if (
                        streak >
                        preferenceManager.getLongestStreak()
                    ) {

                        preferenceManager.saveLongestStreak(
                            streak
                        )
                    }
                }

                else -> {

                    preferenceManager.saveCurrentStreak(1)
                }
            }
        }

        preferenceManager.saveLastCompletionDate(
            currentTime
        )
    }
    private fun validateStreak() {

        val lastCompletionDate =
            preferenceManager.getLastCompletionDate()

        if (lastCompletionDate == 0L) {
            return
        }

        val currentTime =
            System.currentTimeMillis()

        val daysMissed =
            StreakUtils.daysBetween(
                lastCompletionDate,
                currentTime
            )

        if (daysMissed > 1) {

            val freezeDays =
                preferenceManager.getFreezeDays()

            if (freezeDays > 0) {

                preferenceManager.saveFreezeDays(
                    freezeDays - 1
                )

            } else {

                preferenceManager.saveCurrentStreak(
                    0
                )
            }
        }
    }
    private fun refreshTaskStats() {

        updateTasksRemaining()

        binding.tvCurrentStreak.text =
            "Current Streak: ${
                preferenceManager.getCurrentStreak()
            } Days"
    }
    private fun updateFreezeDisplay() {

        binding.tvFreezeDays.text =
            "Freeze Days: ${
                preferenceManager.getFreezeDays()
            }"
    }
}