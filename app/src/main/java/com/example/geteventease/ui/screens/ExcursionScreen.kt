package com.example.geteventease.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.geteventease.data.DateFormats
import com.example.geteventease.data.model.Excursion
import com.example.geteventease.ui.components.StatusBadge
import com.example.geteventease.ui.util.eventCategoryVisual
import com.example.geteventease.data.model.EventCategory
import com.example.geteventease.ui.components.CategoryIconBadgeLarge

@Composable
fun ExcursionScreen(excursion: Excursion) {
    val status = excursion.status()
    val visual = eventCategoryVisual(EventCategory.EXCURSION)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        CategoryIconBadgeLarge(visual = visual)
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = excursion.titre,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        StatusBadge(status = status)
        Spacer(modifier = Modifier.height(16.dp))

        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
            Column(modifier = Modifier.padding(16.dp)) {
                DetailLine("Date", DateFormats.formatDate(excursion.date))
                DetailLine("Heure de départ", DateFormats.formatTime(excursion.heureDepart))
                DetailLine("Destination", excursion.destination)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Programme et activités",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        excursion.activites.forEachIndexed { index, activite ->
            Text(
                text = "${index + 1}. $activite",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

@Composable
private fun DetailLine(label: String, value: String) {
    Text(
        text = "$label : $value",
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(vertical = 2.dp)
    )
}
