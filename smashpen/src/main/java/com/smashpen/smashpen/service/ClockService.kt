package com.smashpen.smashpen.service

import org.springframework.stereotype.Service
import java.util.*

@Service
class ClockService {

    fun calcCurrentDatePlusMilliseconds(millis: Long): Date {
        return Date(System.currentTimeMillis() + millis)
    }

}