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
import com.example.geteventease.data.model.SemaineTcoActivite
import com.example.geteventease.ui.components.ContentItemCard
import com.example.geteventease.ui.components.StatusBadge
import com.example.geteventease.ui.components.StaggeredFadeIn
import com.example.geteventease.ui.util.semaineTypeVisual

@Composable
fun SemaineTcoListScreen(
    activites: List<SemaineTcoActivite>,
    onActiviteClick: (SemaineTcoActivite) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(vertical = 8.dp)) {
        itemsIndexed(activites, key = { _, a -> a.id }) { index, activite ->
            StaggeredFadeIn(index = index) {
                ContentItemCard(
                    titre = activite.titre,
                    sousTitre = "${DateFormats.formatDate(activite.date)} · ${activite.lieu}",
                    status = activite.status(),
                    visual = semaineTypeVisual(activite.type),
                    onClick = { onActiviteClick(activite) }
                )
            }
        }
    }
}

@Composable
fun SemaineTcoDetailScreen(activite: SemaineTcoActivite) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        StatusBadge(status = activite.status())
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = activite.titre,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        DetailLine("Date", DateFormats.formatDate(activite.date))
        DetailLine("Lieu", activite.lieu)

        when (activite) {
            is SemaineTcoActivite.Jardinage -> {
                DetailLine("Heure début", DateFormats.formatTime(activite.heureDebut))
                DetailLine("Heure fin", DateFormats.formatTime(activite.heureFin))
            }
            is SemaineTcoActivite.Conference -> {
                DetailLine("Heure", DateFormats.formatTime(activite.heure))
                DetailLine("Intervenant", activite.intervenant)
                DetailLine("Thème", activite.theme)
            }
            is SemaineTcoActivite.AlgoContest -> {
                DetailLine("Heure", DateFormats.formatTime(activite.heure))
            }
            is SemaineTcoActivite.ConcoursMiniProjet -> {
                DetailLine("Heure", DateFormats.formatTime(activite.heure))
                DetailLine("Jury", activite.jury)
                DetailLine("Intervenant partenaire", activite.intervenantPartenaire)
            }
            is SemaineTcoActivite.Esport -> {
                DetailLine("Heure", DateFormats.formatTime(activite.heure))
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Types de jeux :",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                activite.jeux.forEach { DetailLine("•", it) }
            }
            is SemaineTcoActivite.FinalInterclasse -> {
                DetailLine("Heure", DateFormats.formatTime(activite.heure))
                DetailLine("Sports", activite.sports.joinToString(", "))
            }
            is SemaineTcoActivite.GrandeReception -> {
                DetailLine("Début", DateFormats.formatTime(activite.heureDebut))
                DetailLine("Animateur", activite.animateur)
                DetailLine("Invités", activite.invites)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Programmes :",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                activite.programmes.forEach { DetailLine("•", it) }
            }
        }
    }
}

@Composable
private fun DetailLine(label: String, value: String) {
    Text(
        text = if (label == "•") value else "$label : $value",
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}
