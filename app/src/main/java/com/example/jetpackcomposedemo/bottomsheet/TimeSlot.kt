package com.example.jetpackcomposedemo.bottomsheet

data class TimeSlot(
    val label: String,   // e.g. "slot1"  — stored after selection
    val display: String  // e.g. "10:00 - 11:30" — shown in the wheel
) {
    companion object {
        val NONE = TimeSlot(label = "none", display = "None")
    }
}
