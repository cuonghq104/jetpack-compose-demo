package com.example.jetpackcomposedemo.bottomsheet

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import java.time.LocalDate
import java.time.YearMonth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// ── UI state for the date picker ─────────────────────────────────────────────

sealed class DatePickerUiState {
    object Loading : DatePickerUiState()
    data class GetDateSuccess(val unavailableDates: Set<LocalDate>) : DatePickerUiState()
}

// ── ViewModel ────────────────────────────────────────────────────────────────

class BottomSheetViewModel(application: Application) : AndroidViewModel(application) {

    // Static slot display map (label → display string)
    private val slotDisplayMap = mapOf(
        "slot1" to "10:00 - 11:30",
        "slot2" to "12:00 - 13:30",
        "slot3" to "14:00 - 15:30",
        "slot4" to "16:00 - 17:30",
        "slot5" to "18:00 - 19:30"
    )

    val timeSlots: List<TimeSlot> = listOf(TimeSlot.NONE) + slotDisplayMap.map { (label, display) ->
        TimeSlot(label = label, display = display)
    }

    // The value persisted across bottom sheet open/close sessions
    private val _confirmedSlot = MutableLiveData<TimeSlot>(TimeSlot.NONE)
    val confirmedSlot: LiveData<TimeSlot> get() = _confirmedSlot

    // Transient selection while the bottom sheet is open
    private val _pendingSlot = MutableLiveData<TimeSlot>(TimeSlot.NONE)

    val isNoneSelected: LiveData<Boolean> = _pendingSlot.map { it == TimeSlot.NONE }

    /** Called when the sheet opens — reset pending to whatever was last confirmed */
    fun initPending() {
        _pendingSlot.value = _confirmedSlot.value ?: TimeSlot.NONE
    }

    /** Called on each scroll snap inside the bottom sheet */
    fun selectPending(slot: TimeSlot) {
        _pendingSlot.value = slot
    }

    /** Called when the user taps "Chọn" — persist the pending selection */
    fun confirmSelection() {
        _confirmedSlot.value = _pendingSlot.value ?: TimeSlot.NONE
    }

    // ── Date ─────────────────────────────────────────────────────────────────

    private val _confirmedDate = MutableLiveData<LocalDate?>(null)
    val confirmedDate: LiveData<LocalDate?> get() = _confirmedDate

    fun confirmDate(date: LocalDate) {
        // When the date changes reset the confirmed slot so stale selection is cleared
        if (_confirmedDate.value != date) {
            _confirmedSlot.value = TimeSlot.NONE
            _pendingSlot.value   = TimeSlot.NONE
        }
        _confirmedDate.value = date
    }

    // ── Date picker UI state ──────────────────────────────────────────────────

    private val _datePickerUiState = MutableLiveData<DatePickerUiState>()
    val datePickerUiState: LiveData<DatePickerUiState> get() = _datePickerUiState

    // Full parsed data — retained so the Activity can query slots per date
    private var availabilityData: List<DateAvailability> = emptyList()

    /**
     * Returns the TimeSlot list for a given date (labels resolved to display strings).
     * Includes TimeSlot.NONE as the first entry so the wheel has a "no selection" option.
     * Returns emptyList() if the date has no data yet.
     */
    fun slotsForDate(date: LocalDate): List<TimeSlot> {
        val labels = availabilityData.firstOrNull { it.date == date }?.timeslots
            ?: return emptyList()
        return listOf(TimeSlot.NONE) + labels.mapNotNull { label ->
            slotDisplayMap[label]?.let { display -> TimeSlot(label = label, display = display) }
        }
    }

    /**
     * Loads available dates & slots from assets/available_dates.json.
     * Sets Loading immediately, parses JSON on IO thread, then emits GetDateSuccess
     * with the set of dates that have no timeslots (unavailable).
     */
    fun loadAvailableDateAndSlot() {
        viewModelScope.launch {
            _datePickerUiState.value = DatePickerUiState.Loading

            val (entries, unavailableDates) = withContext(Dispatchers.IO) {
                delay(5_000L)
                val allEntries = DateAvailabilityParser.parse(getApplication())

                // Dates present in JSON with at least one slot → available
                val availableInJson = allEntries
                    .filter { it.timeslots.isNotEmpty() }
                    .map { it.date }
                    .toSet()

                // Full 4-month range (today's month + next 3)
                val today = LocalDate.now()
                val allDatesInRange = (0..3).flatMap { monthOffset ->
                    val ym = YearMonth.from(today).plusMonths(monthOffset.toLong())
                    (1..ym.lengthOfMonth()).map { ym.atDay(it) }
                }.toSet()

                // Dates within the next 10 days (today inclusive) are also disabled
                val blockedRange = (0..10).map { today.plusDays(it.toLong()) }.toSet()

                // Unavailable = not in availableInJson OR within the blocked range
                val unavailable = (allDatesInRange - availableInJson) + (allDatesInRange intersect blockedRange)

                allEntries to unavailable
            }

            availabilityData = entries
            _datePickerUiState.value = DatePickerUiState.GetDateSuccess(unavailableDates)
        }
    }
}
