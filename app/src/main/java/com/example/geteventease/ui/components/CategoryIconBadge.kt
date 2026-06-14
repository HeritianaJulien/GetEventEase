package com.example.geteventease.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.geteventease.ui.util.CategoryVisual

@Composable
fun CategoryIconBadge(
    visual: CategoryVisual,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp
) {
    Box(
        modifier = modifier
            .size(size)
            .shadow(elevation = 8.dp, shape = CircleShape, ambientColor = visual.tint, spotColor = visual.tint)
            .clip(CircleShape)
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        visual.containerColor,
                        visual.containerColor.copy(alpha = 0.7f)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = visual.icon,
            contentDescription = null,
            tint = visual.tint,
            modifier = Modifier.size(size * 0.5f)
        )
    }
}

@Composable
fun CategoryIconBadgeLarge(
    visual: CategoryVisual,
    modifier: Modifier = Modifier
) {
    CategoryIconBadge(visual = visual, modifier = modifier, size = 80.dp)
}
