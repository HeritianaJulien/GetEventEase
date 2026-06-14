package com.example.geteventease.ui.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.OutdoorGrill
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SportsBasketball
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.geteventease.data.model.EventCategory
import com.example.geteventease.data.model.FilierePedagogie
import com.example.geteventease.data.model.SemaineActiviteType
import com.example.geteventease.data.model.Sport
import com.example.geteventease.ui.theme.GetAccent
import com.example.geteventease.ui.theme.GetGreen
import com.example.geteventease.ui.theme.GetGreenDark
import com.example.geteventease.ui.theme.GetGreenLight

data class CategoryVisual(
    val icon: ImageVector,
    val tint: Color,
    val containerColor: Color
)

object CategoryVisuals {
    fun getVisual(categoryName: String): CategoryVisual {
        return when (categoryName) {
            "GTECH" -> filiereVisual(FilierePedagogie.GTECH)
            "GTALK" -> filiereVisual(FilierePedagogie.GTALK)
            "Sport" -> eventCategoryVisual(EventCategory.MATCH_INTERCLASSE)
            "Excursion" -> eventCategoryVisual(EventCategory.EXCURSION)
            "Conférence" -> semaineTypeVisual(SemaineActiviteType.CONFERENCE)
            "PEDAGOGIE" -> eventCategoryVisual(EventCategory.PEDAGOGIE)
            "SEMAINE_TCO" -> eventCategoryVisual(EventCategory.SEMAINE_TCO)
            "MATCH_INTERCLASSE" -> eventCategoryVisual(EventCategory.MATCH_INTERCLASSE)
            "EXCURSION" -> eventCategoryVisual(EventCategory.EXCURSION)
            else -> CategoryVisual(Icons.Default.Event, GetGreen, GetGreen.copy(alpha = 0.15f))
        }
    }
}

fun eventCategoryVisual(category: EventCategory): CategoryVisual = when (category) {
    EventCategory.EXCURSION ->
        CategoryVisual(Icons.Default.OutdoorGrill, GetGreen, GetGreen.copy(alpha = 0.15f))
    EventCategory.MATCH_INTERCLASSE ->
        CategoryVisual(Icons.Default.SportsSoccer, GetGreenDark, GetGreenLight.copy(alpha = 0.2f))
    EventCategory.SEMAINE_TCO ->
        CategoryVisual(Icons.Default.CalendarMonth, GetAccent, GetAccent.copy(alpha = 0.15f))
    EventCategory.PEDAGOGIE ->
        CategoryVisual(Icons.Default.School, GetGreenDark, Color(0xFFE8F5E9))
}

fun sportVisual(sport: Sport): CategoryVisual = when (sport) {
    Sport.FOOTBALL -> CategoryVisual(Icons.Default.SportsSoccer, GetGreen, GetGreen.copy(alpha = 0.15f))
    Sport.BASKETBALL -> CategoryVisual(Icons.Default.SportsBasketball, GetAccent, GetAccent.copy(alpha = 0.15f))
}

fun semaineTypeVisual(type: SemaineActiviteType): CategoryVisual = when (type) {
    SemaineActiviteType.JARDINAGE ->
        CategoryVisual(Icons.Default.Star, GetGreen, GetGreen.copy(alpha = 0.15f))
    SemaineActiviteType.CONFERENCE ->
        CategoryVisual(Icons.Default.Event, GetGreenDark, GetGreenLight.copy(alpha = 0.2f))
    SemaineActiviteType.ALGO_CONTEST ->
        CategoryVisual(Icons.Default.Code, GetGreenDark, GetGreen.copy(alpha = 0.12f))
    SemaineActiviteType.CONCOURS_MINI_PROJET ->
        CategoryVisual(Icons.Default.Code, GetAccent, GetAccent.copy(alpha = 0.15f))
    SemaineActiviteType.ESPORT ->
        CategoryVisual(Icons.Default.VideogameAsset, GetAccent, GetAccent.copy(alpha = 0.15f))
    SemaineActiviteType.FINAL_INTERCLASSE ->
        CategoryVisual(Icons.Default.SportsSoccer, GetGreenDark, GetGreenLight.copy(alpha = 0.2f))
    SemaineActiviteType.GRANDE_RECEPTION ->
        CategoryVisual(Icons.Default.Star, GetGreen, GetGreen.copy(alpha = 0.15f))
}

fun filiereVisual(filiere: FilierePedagogie): CategoryVisual = when (filiere) {
    FilierePedagogie.GTECH ->
        CategoryVisual(Icons.Default.School, GetGreenDark, Color(0xFFE8F5E9))
    FilierePedagogie.GTALK ->
        CategoryVisual(Icons.Default.Language, GetAccent, GetAccent.copy(alpha = 0.15f))
}
