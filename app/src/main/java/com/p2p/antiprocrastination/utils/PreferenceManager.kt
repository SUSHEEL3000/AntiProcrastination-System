package com.p2p.antiprocrastination.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.p2p.antiprocrastination.model.Task

class PreferenceManager(context: Context) {

    private val preferences =
        context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
    private val gson = Gson()

    fun getXp(): Int {
        return preferences.getInt(KEY_XP, 0)
    }

    fun saveXp(xp: Int) {
        preferences.edit()
            .putInt(KEY_XP, xp)
            .apply()
    }
    fun saveTasks(tasks: List<Task>) {

        val json = gson.toJson(tasks)

        preferences.edit()
            .putString(KEY_TASKS, json)
            .apply()
    }

    fun getTasks(): MutableList<Task> {

        val json =
            preferences.getString(
                KEY_TASKS,
                null
            ) ?: return mutableListOf()

        val type =
            object : TypeToken<MutableList<Task>>() {}.type

        return gson.fromJson(json, type)
    }
    fun addXp(amount: Int) {

        val currentXp = getXp()

        saveXp(currentXp + amount)
    }
    fun getCurrentStreak(): Int {
        return preferences.getInt(
            KEY_CURRENT_STREAK,
            0
        )
    }

    fun saveCurrentStreak(streak: Int) {
        preferences.edit()
            .putInt(
                KEY_CURRENT_STREAK,
                streak
            )
            .apply()
    }

    fun getLongestStreak(): Int {
        return preferences.getInt(
            KEY_LONGEST_STREAK,
            0
        )
    }

    fun saveLongestStreak(streak: Int) {
        preferences.edit()
            .putInt(
                KEY_LONGEST_STREAK,
                streak
            )
            .apply()
    }

    fun getLastCompletionDate(): Long {
        return preferences.getLong(
            KEY_LAST_COMPLETION_DATE,
            0L
        )
    }

    fun saveLastCompletionDate(
        date: Long
    ) {
        preferences.edit()
            .putLong(
                KEY_LAST_COMPLETION_DATE,
                date
            )
            .apply()
    }
    fun getLastResetDate(): Long {

        return preferences.getLong(
            KEY_LAST_RESET_DATE,
            0L
        )
    }

    fun saveLastResetDate(
        date: Long
    ) {

        preferences.edit()
            .putLong(
                KEY_LAST_RESET_DATE,
                date
            )
            .apply()
    }

    fun getFreezeDays(): Int {

        return preferences.getInt(
            KEY_FREEZE_DAYS,
            3
        )
    }

    fun saveFreezeDays(
        count: Int
    ) {

        preferences.edit()
            .putInt(
                KEY_FREEZE_DAYS,
                count
            )
            .apply()
    }
    companion object {
        private const val KEY_FREEZE_DAYS =
            "freeze_days"
        private const val KEY_LAST_RESET_DATE =
            "last_reset_date"
        private const val KEY_TASKS = "tasks"
        private const val PREF_NAME =
            "anti_procrastination_prefs"
        private const val KEY_CURRENT_STREAK = "current_streak"

        private const val KEY_LONGEST_STREAK = "longest_streak"

        private const val KEY_LAST_COMPLETION_DATE = "last_completion_date"
        private const val KEY_XP =
            "xp"
    }
}