package com.example.desafiobtg.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun getYearForDate(releaseDate: String?): String? {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = format.parse(releaseDate)
        val calendar = Calendar.getInstance().apply {
            time = date
        }
        return calendar.get(Calendar.YEAR).toString()
    }

}