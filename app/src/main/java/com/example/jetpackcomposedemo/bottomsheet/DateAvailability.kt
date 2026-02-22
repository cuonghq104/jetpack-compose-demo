package com.example.jetpackcomposedemo.bottomsheet

import android.content.Context
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class DateAvailability(
    val date: LocalDate,
    val timeslots: List<String>   // empty list → unavailable
)

object DateAvailabilityParser {

    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    fun parse(context: Context): List<DateAvailability> {
        val json = context.assets.open("available_dates.json")
            .bufferedReader()
            .use { it.readText() }

        val root  = JSONObject(json)
        val array = root.getJSONArray("data")

        return (0 until array.length()).map { i ->
            val obj      = array.getJSONObject(i)
            val date     = LocalDate.parse(obj.getString("date"), formatter)
            val slotArr  = obj.getJSONArray("timeslot")
            val timeslots = (0 until slotArr.length()).map { slotArr.getString(it) }
            DateAvailability(date = date, timeslots = timeslots)
        }.sortedBy { it.date }
    }
}
