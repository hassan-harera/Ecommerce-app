package com.harera.repository.mapper

import com.google.firebase.Timestamp
import java.util.*

object TimestampMapper {

    fun toTimestamp(date: Date): Timestamp {
        return Timestamp(date)
    }

    fun toDate(timestamp: Timestamp): Date {
        return timestamp.toDate()
    }
}