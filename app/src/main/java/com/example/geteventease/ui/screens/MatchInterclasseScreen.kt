package com.example.geteventease.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.geteventease.data.DateFormats
import com.example.geteventease.data.model.MatchInterclasse
import com.example.geteventease.ui.components.CategoryIconBadgeLarge
import com.example.geteventease.ui.components.ContentItemCard
import com.example.geteventease.ui.components.StatusBadge
import com.example.geteventease.ui.components.StaggeredFadeIn
import com.example.geteventease.ui.theme.BrandPrimary
import com.example.geteventease.ui.theme.BrandPrimaryVariant
import com.example.geteventease.ui.util.sportVisual

@Composable
fun MatchInterclasseListScreen(
    matchs: List<MatchInterclasse>,
    onMatchClick: (MatchInterclasse) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 12.dp)
    ) {
        itemsIndexed(matchs, key = { _, m -> m.id }) { index, match ->
            StaggeredFadeIn(index = index) {
                ContentItemCard(
                    titre = match.titre,
                    sousTitre = "${DateFormats.formatDate(match.date)} · ${match.lieu}",
                    status = match.status(),
                    visual = sportVisual(match.sport),
                    onClick = { onMatchClick(match) }
                )
            }
        }
    }
}

@Composable
fun MatchInterclasseDetailScreen(match: MatchInterclasse) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        // Hero Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(BrandPrimary, BrandPrimaryVariant)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CategoryIconBadgeLarge(visual = sportVisual(match.sport))
                Spacer(modifier = Modifier.height(16.dp))
                StatusBadge(status = match.status())
            }
        }

        // Details Section
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (-32).dp),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .padding(32.dp)
            ) {
                Text(
                    text = match.titre,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                Spacer(modifier = Modifier.height(24.dp))

                DetailCard(
                    items = listOf(
                        "Sport" to match.sport.label,
                        "Phase" to match.phase,
                        "Équipes" to match.equipesInfo
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                DetailCard(
                    items = listOf(
                        "Date" to DateFormats.formatDate(match.date),
                        "Heure" to DateFormats.formatTime(match.heure),
                        "Lieu" to match.lieu
                    )
                )
            }
        }
    }
}

@Composable
private fun DetailCard(items: List<Pair<String, String>>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            items.forEachIndexed { index, (label, value) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = value,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                if (index < items.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}
