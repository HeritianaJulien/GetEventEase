package com.example.geteventease.data.local

import androidx.room.*
import com.example.geteventease.data.model.EventStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Query("SELECT * FROM events ORDER BY date ASC, time ASC")
    fun getAllEvents(): Flow<List<EventEntity>>

    @Query("SELECT * FROM events WHERE status = :status ORDER BY date ASC, time ASC")
    fun getEventsByStatus(status: EventStatus = EventStatus.PUBLISHED): Flow<List<EventEntity>>

    @Query("SELECT * FROM events WHERE id = :id")
    suspend fun getEventById(id: Int): EventEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventEntity)

    @Update
    suspend fun updateEvent(event: EventEntity)

    @Delete
    suspend fun deleteEvent(event: EventEntity)
    
    @Query("SELECT COUNT(*) FROM events")
    fun getEventsCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM events WHERE status = :status")
    fun getCountByStatus(status: EventStatus): Flow<Int>

    @Query("UPDATE events SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Int, isFavorite: Boolean)
}
