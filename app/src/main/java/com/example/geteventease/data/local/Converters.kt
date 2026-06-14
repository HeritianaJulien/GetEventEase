package com.example.geteventease.data.local

import androidx.room.TypeConverter
import com.example.geteventease.data.model.EventStatus
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Converters {
    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    private val timeFormatter = DateTimeFormatter.ISO_LOCAL_TIME

    @TypeConverter
    fun fromDate(value: LocalDate?): String? = value?.format(dateFormatter)

    @TypeConverter
    fun toDate(value: String?): LocalDate? = value?.let { LocalDate.parse(it, dateFormatter) }

    @TypeConverter
    fun fromTime(value: LocalTime?): String? = value?.format(timeFormatter)

    @TypeConverter
    fun toTime(value: String?): LocalTime? = value?.let { LocalTime.parse(it, timeFormatter) }

    @TypeConverter
    fun fromStatus(status: EventStatus): String = status.name

    @TypeConverter
    fun toStatus(value: String): EventStatus = EventStatus.valueOf(value)
}
