package com.adel.facetimeclone.data.utils

import org.jetbrains.annotations.NotNull
import java.text.SimpleDateFormat
import java.util.*

class DateAndTimeUtils {
    private val SECOND_MILLIS: Long = 1000
    private val MINUTE_MILLIS: Long = 60 * SECOND_MILLIS
    private val HOUR_MILLIS: Long = 60 * MINUTE_MILLIS
    private val DAY_MILLIS: Long = 24 * HOUR_MILLIS
    private val suffix = "Ago"
    fun covertTimeToText(@NotNull time: String): String? {
        if (time.toLong() < 1000000000000L) {
            throw(Exception("Enter time in milliseconds"))
        }
        val now: Long = Date().time
        val dateDiff = now - (time.toLong())

        return if (dateDiff < 24 * HOUR_MILLIS)
            "Today"
        else if (dateDiff < 48 * HOUR_MILLIS) {
            "Yesterday"
        } else {
            if (dateDiff > 30 * DAY_MILLIS) {
                if (dateDiff > 360 * DAY_MILLIS) {
                    ((dateDiff / DAY_MILLIS) / 360).toString() + " Years " + suffix
                } else {
                    ((dateDiff / DAY_MILLIS) / 30).toString() + " Months " + suffix
                }
            } else {
                ((dateDiff / DAY_MILLIS)).toString() + " days " + suffix
            }
        }
    }
    fun fromMillisSecondToDate(@NotNull time: String): String? {
        if (time.toLong() < 1000000000000L) {
            throw(Exception("Enter time in milliseconds"))
        }
        val tempTime=covertTimeToText(time)
        return if (tempTime.equals("Today")||tempTime.equals("Yesterday"))
            tempTime
         else {
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
            simpleDateFormat.format(time.toLong())
        }
    }
}