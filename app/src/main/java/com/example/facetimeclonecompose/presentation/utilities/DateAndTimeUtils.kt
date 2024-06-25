package com.example.facetimeclonecompose.presentation.utilities

import java.util.Date

object DateAndTimeUtils {
    private const val SECOND_MILLIS: Long = 1000
    private const val MINUTE_MILLIS: Long = 60 * SECOND_MILLIS
    private const val HOUR_MILLIS: Long = 60 * MINUTE_MILLIS
    private const val DAY_MILLIS: Long = 24 * HOUR_MILLIS
    private const val SUFFIX = "Ago"
    fun covertTimeToText(time: String): String {
        if(time == "0") return "Just Now"

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
                    ((dateDiff / DAY_MILLIS) / 360).toString() + " Years " + SUFFIX
                } else {
                    ((dateDiff / DAY_MILLIS) / 30).toString() + " Months " + SUFFIX
                }
            } else {
                ((dateDiff / DAY_MILLIS)).toString() + " days " + SUFFIX
            }
        }
    }
//    fun fromMillisSecondToDate(time: String): String? {
//        if (time.toLong() < 1000000000000L) {
//            throw(Exception("Enter time in milliseconds"))
//        }
//        val tempTime=covertTimeToText(time)
//        return if (tempTime.equals("Today")||tempTime.equals("Yesterday"))
//            tempTime
//         else {
//            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
//            simpleDateFormat.format(time.toLong())
//        }
//    }
}