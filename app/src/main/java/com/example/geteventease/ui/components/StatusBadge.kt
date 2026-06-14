package com.example.geteventease.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.geteventease.data.EventStatus
import com.example.geteventease.ui.theme.*

@Composable
fun StatusBadge(status: EventStatus) {
    val colors = when (status) {
        EventStatus.PASSE -> Color(0xFFF3F4F6) to Color(0xFF6B7280)
        EventStatus.EN_COURS -> BrandSecondary.copy(alpha = 0.1f) to BrandSecondary
        EventStatus.A_VENIR -> BrandPrimary.copy(alpha = 0.1f) to BrandPrimary
    }
    val container = colors.first
    val content = colors.second
    
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(container)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = status.label,
            style = MaterialTheme.typography.labelMedium,
            color = content,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )
    }
}
