package com.example.geteventease.data.model

import com.example.geteventease.data.EventStatus
import com.example.geteventease.data.StatusResolver
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

enum class SemaineActiviteType(val label: String) {
    JARDINAGE("Jardinage"),
    CONFERENCE("Conférence"),
    ALGO_CONTEST("Algo Contest"),
    CONCOURS_MINI_PROJET("Concours de mini-projet"),
    ESPORT("Esport"),
    FINAL_INTERCLASSE("Final interclasse"),
    GRANDE_RECEPTION("Grande réception")
}

sealed class SemaineTcoActivite {
    abstract val id: Int
    abstract val type: SemaineActiviteType
    abstract val titre: String
    abstract val date: LocalDate
    abstract val lieu: String

    abstract fun status(now: LocalDateTime = LocalDateTime.now()): EventStatus

    data class Jardinage(
        override val id: Int,
        override val date: LocalDate,
        val heureDebut: LocalTime,
        val heureFin: LocalTime,
        override val lieu: String = "Département de la télécommunication"
    ) : SemaineTcoActivite() {
        override val type = SemaineActiviteType.JARDINAGE
        override val titre = type.label

        override fun status(now: LocalDateTime) =
            StatusResolver.forSingleDay(date, heureDebut, heureFin, now)
    }

    data class Conference(
        override val id: Int,
        override val date: LocalDate,
        val heure: LocalTime,
        override val lieu: String,
        val intervenant: String,
        val theme: String
    ) : SemaineTcoActivite() {
        override val type = SemaineActiviteType.CONFERENCE
        override val titre = "$type — $theme"

        override fun status(now: LocalDateTime) =
            StatusResolver.forSingleDay(date, heure, null, now)
    }

    data class AlgoContest(
        override val id: Int,
        override val date: LocalDate,
        val heure: LocalTime,
        override val lieu: String
    ) : SemaineTcoActivite() {
        override val type = SemaineActiviteType.ALGO_CONTEST
        override val titre = type.label

        override fun status(now: LocalDateTime) =
            StatusResolver.forSingleDay(date, heure, null, now)
    }

    data class ConcoursMiniProjet(
        override val id: Int,
        override val date: LocalDate,
        val heure: LocalTime,
        override val lieu: String = "Vitrine numérique",
        val jury: String,
        val intervenantPartenaire: String
    ) : SemaineTcoActivite() {
        override val type = SemaineActiviteType.CONCOURS_MINI_PROJET
        override val titre = type.label

        override fun status(now: LocalDateTime) =
            StatusResolver.forSingleDay(date, heure, null, now)
    }

    data class Esport(
        override val id: Int,
        override val date: LocalDate,
        val heure: LocalTime,
        override val lieu: String,
        val jeux: List<String>
    ) : SemaineTcoActivite() {
        override val type = SemaineActiviteType.ESPORT
        override val titre = type.label

        override fun status(now: LocalDateTime) =
            StatusResolver.forSingleDay(date, heure, null, now)
    }

    data class FinalInterclasse(
        override val id: Int,
        override val date: LocalDate,
        val heure: LocalTime,
        override val lieu: String,
        val sports: List<String>
    ) : SemaineTcoActivite() {
        override val type = SemaineActiviteType.FINAL_INTERCLASSE
        override val titre = type.label

        override fun status(now: LocalDateTime) =
            StatusResolver.forSingleDay(date, heure, null, now)
    }

    data class GrandeReception(
        override val id: Int,
        override val date: LocalDate,
        val heureDebut: LocalTime,
        override val lieu: String,
        val animateur: String,
        val programmes: List<String>,
        val invites: String
    ) : SemaineTcoActivite() {
        override val type = SemaineActiviteType.GRANDE_RECEPTION
        override val titre = type.label

        override fun status(now: LocalDateTime) =
            StatusResolver.forSingleDay(date, heureDebut, null, now)
    }
}
