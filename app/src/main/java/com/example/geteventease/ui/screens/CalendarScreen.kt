package com.example.geteventease.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.geteventease.data.FakeData
import com.example.geteventease.data.model.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun CalendarScreen() {
    val eventsByDate = remember {
        val allEvents = mutableListOf<CalendarEvent>()
        
        // Collect Excursions
        FakeData.excursions.forEach { 
            allEvents.add(CalendarEvent(it.date, it.titre, "Excursion", Color(0xFF4CAF50))) 
        }
        
        // Collect Matchs
        FakeData.matchsInterclasse.forEach {
            allEvents.add(CalendarEvent(it.date, it.titre, it.lieu, Color(0xFF1565C0)))
        }
        
        // Collect Semaine TCO
        FakeData.semaineTcoActivites.forEach {
            allEvents.add(CalendarEvent(it.date, it.titre, it.lieu, Color(0xFFFF9100)))
        }

        // Collect Pédagogie (Recurring)
        FakeData.pedagogieFilieres.forEach { ped ->
            var currentDate = ped.dateDebut
            while (!currentDate.isAfter(ped.dateFin)) {
                allEvents.add(
                    CalendarEvent(
                        currentDate,
                        "Pédagogie — ${ped.filiere.label}",
                        "Programmes en alternance",
                        Color(0xFF673AB7)
                    )
                )
                currentDate = currentDate.plusWeeks(1)
            }
        }
        
        allEvents.groupBy { it.date }.toSortedMap()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        eventsByDate.forEach { (date, events) ->
            item {
                DateHeader(date)
            }
            items(events) { event ->
                CalendarEventItem(event)
            }
        }
    }
}

data class CalendarEvent(
    val date: LocalDate,
    val title: String,
    val subtitle: String,
    val color: Color
)

@Composable
private fun DateHeader(date: LocalDate) {
    val formatter = remember { DateTimeFormatter.ofPattern("EEEE d MMMM yyyy", Locale.FRENCH) }
    Text(
        text = date.format(formatter).replaceFirstChar { it.uppercase() },
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
private fun CalendarEventItem(event: CalendarEvent) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .fillMaxHeight()
                    .background(event.color)
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = event.subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
