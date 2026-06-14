package com.example.geteventease.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.geteventease.data.EventRepository
import com.example.geteventease.data.local.AppDatabase
import com.example.geteventease.data.local.EventEntity
import com.example.geteventease.ui.components.ContentItemCard
import com.example.geteventease.ui.util.CategoryVisuals
import com.example.geteventease.ui.viewmodel.HomeViewModel
import com.example.geteventease.data.StatusResolver
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun NotificationsScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Notifications Screen", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun ProfileScreen(onAdminClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier.size(100.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Étudiant GET", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text("etudiant@get.mg", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Hidden Admin Button
        TextButton(
            onClick = onAdminClick,
            modifier = Modifier.alpha(0.1f) // Very faint
        ) {
            Text("Admin Access")
        }
    }
}

@Composable
fun EventsScreen(
    onEventClick: (EventEntity) -> Unit
) {
    val context = LocalContext.current
    val repository = remember { EventRepository(AppDatabase.getDatabase(context).eventDao()) }
    val viewModel: HomeViewModel = viewModel(factory = object : androidx.lifecycle.ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(repository) as T
        }
    })

    val events by viewModel.publishedEvents.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Tous les événements",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        
        if (events.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(events) { event ->
                    ContentItemCard(
                        titre = event.title,
                        sousTitre = "${event.date} • ${event.location}",
                        status = StatusResolver.forSingleDay(event.date, event.time),
                        visual = CategoryVisuals.getVisual(event.category),
                        onClick = { onEventClick(event) }
                    )
                }
            }
        }
    }
}

@Composable
fun EventsListPlaceholderScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Liste de tous les événements", style = MaterialTheme.typography.headlineMedium)
    }
}
