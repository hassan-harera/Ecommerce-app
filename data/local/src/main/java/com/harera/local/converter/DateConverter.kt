package com.harera.local.converter

import androidx.room.TypeConverter
import java.util.*

internal object DateConverter {
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return if (dateLong == null) null else Date(dateLong)
    }

    @TypeConverter
    fun toLong(date: Date?): Long? {
        return date?.time
    }
}