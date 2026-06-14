package com.example.geteventease.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.geteventease.data.model.EventStatus
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val date: LocalDate,
    val time: LocalTime,
    val location: String,
    val category: String,
    val subCategory: String? = null,
    val imagePath: String? = null,
    val bannerImagePath: String? = null,
    val status: EventStatus = EventStatus.DRAFT,
    val timestamp: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false
)
