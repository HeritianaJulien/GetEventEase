package com.example.geteventease.data

import com.example.geteventease.data.model.Excursion
import com.example.geteventease.data.model.FilierePedagogie
import com.example.geteventease.data.model.MatchInterclasse
import com.example.geteventease.data.model.PedagogieFiliere
import com.example.geteventease.data.model.Sport
import com.example.geteventease.data.model.SemaineTcoActivite
import java.time.LocalDate
import java.time.LocalTime

object FakeData {

    private fun d(day: Int, month: Int, year: Int = 2026) = LocalDate.of(year, month, day)
    private fun t(hour: Int, minute: Int = 0) = LocalTime.of(hour, minute)

    val excursions = listOf(
        Excursion(
            id = 1,
            date = d(18, 10),
            heureDepart = t(6, 0),
            destination = "Site de reboisement — région Analamanga",
            activites = listOf(
                "Reboisement",
                "Match demi-finale football",
                "Match demi-finale basketball",
                "Jeux d'intégration",
                "Déjeuner",
                "Jeux"
            )
        )
    )

    private const val EQUIPES_INFO =
        "13 équipes (3 groupes de 4 classes, 1 groupe de 5 classes)"

    val matchsInterclasse = listOf(
        MatchInterclasse(
            id = 1,
            sport = Sport.FOOTBALL,
            phase = "Phase de groupe",
            equipesInfo = EQUIPES_INFO,
            date = d(5, 11),
            heure = t(14, 0),
            lieu = "Terrain ESPA"
        ),
        MatchInterclasse(
            id = 2,
            sport = Sport.BASKETBALL,
            phase = "Phase de groupe",
            equipesInfo = EQUIPES_INFO,
            date = d(6, 11),
            heure = t(15, 0),
            lieu = "Terrain ESPA"
        )
    )

    val semaineTcoActivites: List<SemaineTcoActivite> = listOf(
        SemaineTcoActivite.Jardinage(
            id = 1,
            date = d(10, 11),
            heureDebut = t(8, 0),
            heureFin = t(11, 0)
        ),
        SemaineTcoActivite.Conference(
            id = 2,
            date = d(10, 11),
            heure = t(14, 0),
            lieu = "Amphithéâtre ESPA",
            intervenant = "M. Randria — Orange Madagascar",
            theme = "Réseaux mobiles et télécommunications"
        ),
        SemaineTcoActivite.AlgoContest(
            id = 3,
            date = d(11, 11),
            heure = t(9, 0),
            lieu = "Salle informatique ESPA"
        ),
        SemaineTcoActivite.ConcoursMiniProjet(
            id = 4,
            date = d(12, 11),
            heure = t(9, 0),
            jury = "Professeurs GET",
            intervenantPartenaire = "Partenaire industriel Télécom"
        ),
        SemaineTcoActivite.Esport(
            id = 5,
            date = d(12, 11),
            heure = t(14, 0),
            lieu = "Espace gaming ESPA",
            jeux = listOf("PES 2026", "Tekken", "Blur", "Échecs")
        ),
        SemaineTcoActivite.FinalInterclasse(
            id = 6,
            date = d(13, 11),
            heure = t(14, 0),
            lieu = "Terrain ESPA",
            sports = listOf("Football", "Basketball")
        ),
        SemaineTcoActivite.GrandeReception(
            id = 7,
            date = d(14, 11),
            heureDebut = t(18, 0),
            lieu = "Hall ESPA",
            animateur = "Bureau GET",
            programmes = listOf("Entrée", "Remise des trophées", "Discours", "Cocktail"),
            invites = "Étudiants, enseignants, partenaires et alumni"
        )
    )

    val pedagogieFilieres = listOf(
        PedagogieFiliere(
            id = 1,
            filiere = FilierePedagogie.GTECH,
            dateDebut = d(24, 3),
            dateFin = d(2, 6),
            programmes = listOf(
                "Initiation au programme python",
                "Automatisation des tâches sur windows",
                "Bases de l'électronique et IoT"
            )
        ),
        PedagogieFiliere(
            id = 2,
            filiere = FilierePedagogie.GTALK,
            dateDebut = d(25, 3),
            dateFin = d(3, 6),
            programmes = listOf(
                "Communication orale en anglais",
                "Vocabulaire technique télécom",
                "Préparation aux entretiens professionnels"
            )
        )
    )

    fun semaineActiviteById(id: Int): SemaineTcoActivite? =
        semaineTcoActivites.find { it.id == id }

    fun matchById(id: Int): MatchInterclasse? =
        matchsInterclasse.find { it.id == id }

    fun pedagogieById(id: Int): PedagogieFiliere? =
        pedagogieFilieres.find { it.id == id }
}
