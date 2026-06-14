package com.example.geteventease.data

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

object StatusResolver {

    fun forSingleDay(
        date: LocalDate,
        heureDebut: LocalTime? = null,
        heureFin: LocalTime? = null,
        now: LocalDateTime = LocalDateTime.now()
    ): EventStatus {
        val today = now.toLocalDate()
        when {
            date.isBefore(today) -> return EventStatus.PASSE
            date.isAfter(today) -> return EventStatus.A_VENIR
        }
        if (heureDebut == null && heureFin == null) return EventStatus.EN_COURS
        val current = now.toLocalTime()
        val fin = heureFin ?: LocalTime.MAX
        val debut = heureDebut ?: LocalTime.MIN
        return when {
            current.isBefore(debut) -> EventStatus.A_VENIR
            current.isAfter(fin) -> EventStatus.PASSE
            else -> EventStatus.EN_COURS
        }
    }

    fun forDateRange(
        dateDebut: LocalDate,
        dateFin: LocalDate,
        now: LocalDate = LocalDate.now()
    ): EventStatus = when {
        now.isBefore(dateDebut) -> EventStatus.A_VENIR
        now.isAfter(dateFin) -> EventStatus.PASSE
        else -> EventStatus.EN_COURS
    }
}
