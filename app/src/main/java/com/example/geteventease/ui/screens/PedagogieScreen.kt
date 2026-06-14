package com.example.geteventease.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.geteventease.data.DateFormats
import com.example.geteventease.data.model.PedagogieFiliere
import com.example.geteventease.ui.components.ContentItemCard
import com.example.geteventease.ui.components.StatusBadge
import com.example.geteventease.ui.components.StaggeredFadeIn
import com.example.geteventease.ui.util.filiereVisual

@Composable
fun PedagogieListScreen(
    filieres: List<PedagogieFiliere>,
    onFiliereClick: (PedagogieFiliere) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(vertical = 8.dp)) {
        itemsIndexed(filieres, key = { _, f -> f.id }) { index, filiere ->
            StaggeredFadeIn(index = index) {
                ContentItemCard(
                    titre = filiere.titre,
                    sousTitre = "${filiere.filiere.jourRecurrence} · ${
                        DateFormats.formatDateRange(filiere.dateDebut, filiere.dateFin)
                    }",
                    status = filiere.status(),
                    visual = filiereVisual(filiere.filiere),
                    onClick = { onFiliereClick(filiere) }
                )
            }
        }
    }
}

@Composable
fun PedagogieDetailScreen(filiere: PedagogieFiliere) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        StatusBadge(status = filiere.status())
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = filiere.filiere.label,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        DetailLine("Récurrence", filiere.filiere.jourRecurrence)
        DetailLine("Date début", DateFormats.formatDate(filiere.dateDebut))
        DetailLine("Date fin", DateFormats.formatDate(filiere.dateFin))
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Programmes en alternance",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        filiere.programmes.forEachIndexed { index, programme ->
            Text(
                text = "${index + 1}. $programme",
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
        modifier = Modifier.padding(vertical = 4.dp)
    )
}
