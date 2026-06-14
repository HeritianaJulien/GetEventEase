package com.example.geteventease.data.model

enum class EventCategory(val title: String, val subtitle: String) {
    EXCURSION(
        title = "Excursion",
        subtitle = "Destination, programme et activités"
    ),
    MATCH_INTERCLASSE(
        title = "Matchs interclasse",
        subtitle = "Football et basketball — phase de groupe"
    ),
    SEMAINE_TCO(
        title = "Semaine de la TCO",
        subtitle = "Jardinage, conférences, esport, finale…"
    ),
    PEDAGOGIE(
        title = "Pédagogie",
        subtitle = "GTECH (mardi) et GTALK (mercredi)"
    )
}
