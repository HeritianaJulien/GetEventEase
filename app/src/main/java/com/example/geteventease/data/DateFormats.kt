package com.example.geteventease.data

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateFormats {
    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRANCE)
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.FRANCE)

    fun formatDate(date: LocalDate): String = date.format(dateFormatter)
    fun formatTime(time: LocalTime): String = time.format(timeFormatter)
    fun formatDateRange(debut: LocalDate, fin: LocalDate): String =
        "${formatDate(debut)} → ${formatDate(fin)}"
}
