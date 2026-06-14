package com.p2p.antiprocrastination.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit

object StreakUtils {

    private fun toLocalDate(
        timeMillis: Long
    ): LocalDate {

        return Instant
            .ofEpochMilli(timeMillis)
            .atZone(
                ZoneId.systemDefault()
            )
            .toLocalDate()
    }

    fun daysBetween(
        oldTime: Long,
        newTime: Long
    ): Long {

        return ChronoUnit.DAYS.between(
            toLocalDate(oldTime),
            toLocalDate(newTime)
        )
    }

    fun isSameDay(
        first: Long,
        second: Long
    ): Boolean {

        return toLocalDate(first) ==
                toLocalDate(second)
    }

    fun isYesterday(
        oldDate: Long,
        newDate: Long
    ): Boolean {

        return daysBetween(
            oldDate,
            newDate
        ) == 1L
    }
}