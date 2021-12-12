package com.harera.ecommerce.local.converter

import androidx.room.TypeConverter
import com.google.firebase.Timestamp

class TimestampConverter {

    @TypeConverter
    fun fromTimestamp(value: Timestamp): Long {
        return value.seconds
    }

    @TypeConverter
    fun dateToTimestamp(date: Long): Timestamp {
        return date.let {
            Timestamp(it, 0)
        }
    }
}