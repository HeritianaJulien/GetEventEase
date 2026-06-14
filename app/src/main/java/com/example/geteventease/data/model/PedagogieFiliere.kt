package com.example.geteventease.data.model

import com.example.geteventease.data.EventStatus
import com.example.geteventease.data.StatusResolver
import java.time.LocalDate

enum class FilierePedagogie(val label: String, val jourRecurrence: String) {
    GTECH("GTECH", "Chaque mardi"),
    GTALK("GTALK", "Chaque mercredi")
}

data class PedagogieFiliere(
    val id: Int,
    val filiere: FilierePedagogie,
    val dateDebut: LocalDate,
    val dateFin: LocalDate,
    val programmes: List<String>
) {
    val titre: String = filiere.label

    fun status(now: LocalDate = LocalDate.now()): EventStatus =
        StatusResolver.forDateRange(dateDebut, dateFin, now)
}
