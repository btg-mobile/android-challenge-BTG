package com.rafaelpereiraramos.challengebtg.commonsandroid

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    companion object {
        fun getMovieYear(dateStr: String): Int {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val calendar = Calendar.getInstance().apply { time = dateFormat.parse(dateStr)!! }

            return calendar.get(Calendar.YEAR)
        }
    }
}