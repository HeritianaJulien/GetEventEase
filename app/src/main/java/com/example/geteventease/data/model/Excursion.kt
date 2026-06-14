package com.example.geteventease.data.model

import com.example.geteventease.data.EventStatus
import com.example.geteventease.data.StatusResolver
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class Excursion(
    val id: Int,
    val date: LocalDate,
    val heureDepart: LocalTime,
    val destination: String,
    val activites: List<String>
) {
    val titre: String = "Excursion GET"

    fun status(now: LocalDateTime = LocalDateTime.now()): EventStatus =
        StatusResolver.forSingleDay(date, heureDepart, null, now)
}
