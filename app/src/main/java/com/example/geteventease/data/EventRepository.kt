package com.example.geteventease.data

import com.example.geteventease.data.local.EventDao
import com.example.geteventease.data.local.EventEntity
import com.example.geteventease.data.model.EventStatus
import com.example.geteventease.data.model.SemaineTcoActivite
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class EventRepository(private val eventDao: EventDao) {
    val allEvents: Flow<List<EventEntity>> = eventDao.getAllEvents()
    val publishedEvents: Flow<List<EventEntity>> = eventDao.getEventsByStatus(EventStatus.PUBLISHED)
    val eventsCount: Flow<Int> = eventDao.getEventsCount()
    
    fun getCountByStatus(status: EventStatus): Flow<Int> = eventDao.getCountByStatus(status)

    suspend fun insert(event: EventEntity) = eventDao.insertEvent(event)
    suspend fun update(event: EventEntity) = eventDao.updateEvent(event)
    suspend fun delete(event: EventEntity) = eventDao.deleteEvent(event)
    suspend fun getEventById(id: Int) = eventDao.getEventById(id)
    
    suspend fun toggleFavorite(id: Int, currentFavorite: Boolean) {
        eventDao.updateFavorite(id, !currentFavorite)
    }

    suspend fun initializeFakeData() {
        val count = eventsCount.first()
        if (count > 0) return // Already initialized

        // Seed Excursions
        FakeData.excursions.forEach { excursion ->
            eventDao.insertEvent(
                EventEntity(
                    title = excursion.titre,
                    description = "Destination: ${excursion.destination}\nActivités: ${excursion.activites.joinToString(", ")}",
                    date = excursion.date,
                    time = excursion.heureDepart,
                    location = excursion.destination,
                    category = "EXCURSION",
                    status = EventStatus.PUBLISHED
                )
            )
        }

        // Seed Matchs
        FakeData.matchsInterclasse.forEach { match ->
            eventDao.insertEvent(
                EventEntity(
                    title = match.titre,
                    description = "${match.phase}\n${match.equipesInfo}",
                    date = match.date,
                    time = match.heure,
                    location = match.lieu,
                    category = "MATCH_INTERCLASSE",
                    subCategory = match.sport.label,
                    status = EventStatus.PUBLISHED
                )
            )
        }

        // Seed Semaine TCO
        FakeData.semaineTcoActivites.forEach { activite ->
            val description = when (activite) {
                is SemaineTcoActivite.Conference -> "Thème: ${activite.theme}\nIntervenant: ${activite.intervenant}"
                is SemaineTcoActivite.ConcoursMiniProjet -> "Jury: ${activite.jury}\nPartenaire: ${activite.intervenantPartenaire}"
                is SemaineTcoActivite.Esport -> "Jeux: ${activite.jeux.joinToString(", ")}"
                is SemaineTcoActivite.FinalInterclasse -> "Sports: ${activite.sports.joinToString(", ")}"
                is SemaineTcoActivite.GrandeReception -> "Animateur: ${activite.animateur}\nInvités: ${activite.invites}"
                is SemaineTcoActivite.Jardinage -> "Entretien du département"
                else -> ""
            }
            
            val time = when (activite) {
                is SemaineTcoActivite.Jardinage -> activite.heureDebut
                is SemaineTcoActivite.Conference -> activite.heure
                is SemaineTcoActivite.AlgoContest -> activite.heure
                is SemaineTcoActivite.ConcoursMiniProjet -> activite.heure
                is SemaineTcoActivite.Esport -> activite.heure
                is SemaineTcoActivite.FinalInterclasse -> activite.heure
                is SemaineTcoActivite.GrandeReception -> activite.heureDebut
            }

            eventDao.insertEvent(
                EventEntity(
                    title = activite.titre,
                    description = description,
                    date = activite.date,
                    time = time,
                    location = activite.lieu,
                    category = "SEMAINE_TCO",
                    subCategory = activite.type.label,
                    status = EventStatus.PUBLISHED
                )
            )
        }

        // Seed Pédagogie
        FakeData.pedagogieFilieres.forEach { ped ->
            var currentDate = ped.dateDebut
            while (!currentDate.isAfter(ped.dateFin)) {
                eventDao.insertEvent(
                    EventEntity(
                        title = "Pédagogie — ${ped.filiere.label}",
                        description = "Programmes: ${ped.programmes.joinToString(", ")}",
                        date = currentDate,
                        time = java.time.LocalTime.of(8, 0),
                        location = "ESPA",
                        category = "PEDAGOGIE",
                        subCategory = ped.filiere.label,
                        status = EventStatus.PUBLISHED
                    )
                )
                currentDate = currentDate.plusWeeks(1)
            }
        }
    }
}
