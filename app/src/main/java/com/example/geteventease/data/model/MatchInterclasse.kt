package com.example.geteventease.data.model

import com.example.geteventease.data.EventStatus
import com.example.geteventease.data.StatusResolver
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

enum class Sport(val label: String) {
    FOOTBALL("Football"),
    BASKETBALL("Basketball")
}

data class MatchInterclasse(
    val id: Int,
    val sport: Sport,
    val phase: String,
    val equipesInfo: String,
    val date: LocalDate,
    val heure: LocalTime,
    val lieu: String
) {
    val titre: String = "Match ${sport.label} — $phase"

    fun status(now: LocalDateTime = LocalDateTime.now()): EventStatus =
        StatusResolver.forSingleDay(date, heure, null, now)
}
