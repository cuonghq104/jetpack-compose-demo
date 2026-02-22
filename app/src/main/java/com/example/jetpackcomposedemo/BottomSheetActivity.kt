package com.example.jetpackcomposedemo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.jetpackcomposedemo.bottomsheet.BottomSheetViewModel
import com.example.jetpackcomposedemo.bottomsheet.DatePickerDialog
import com.example.jetpackcomposedemo.bottomsheet.TimeRangeBottomSheet
import com.example.jetpackcomposedemo.bottomsheet.TimeSlot
import com.example.jetpackcomposedemo.databinding.ActivityBottomSheetBinding
import java.time.format.DateTimeFormatter

class BottomSheetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomSheetBinding
    private val viewModel: BottomSheetViewModel by viewModels()

    private val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBottomSheetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Timeslot row starts disabled until a date is selected
        setTimeslotEnabled(false)

        binding.tvDateWrapper.setOnClickListener {
            viewModel.loadAvailableDateAndSlot()
            openDatePicker()
        }

        binding.tvTimeSlotWrapper.setOnClickListener {
            openTimeRangePicker()
        }

        viewModel.confirmedDate.observe(this) { date ->
            binding.tvDate.text = date?.format(dateFormatter) ?: "Select date"
            setTimeslotEnabled(date != null)
            if (date == null) binding.tvTimeSlot.text = "Select timeslot"
        }

        viewModel.confirmedSlot.observe(this) { slot ->
            binding.tvTimeSlot.text = if (slot == TimeSlot.NONE) "Select timeslot" else slot.display
        }
    }

    private fun setTimeslotEnabled(enabled: Boolean) {
        binding.tvTimeSlotWrapper.isClickable = enabled
        binding.tvTimeSlotWrapper.isFocusable = enabled
        binding.tvTimeSlotWrapper.alpha       = if (enabled) 1f else 0.38f
    }

    private fun openDatePicker() {
        DatePickerDialog(
            context        = this,
            lifecycleOwner = this,
            uiState        = viewModel.datePickerUiState,
            initialDate    = viewModel.confirmedDate.value,
            onConfirm      = { date ->
                viewModel.confirmDate(date)
                Toast.makeText(this, "Đã chọn: ${date.format(dateFormatter)}", Toast.LENGTH_SHORT).show()
            }
        ).show()
    }

    private fun openTimeRangePicker() {
        val selectedDate = viewModel.confirmedDate.value ?: return
        val slots = viewModel.slotsForDate(selectedDate)

        // Keep confirmed slot if it still exists for the new date, else fall back to NONE
        val currentSlot = viewModel.confirmedSlot.value ?: TimeSlot.NONE
        val initialSlot = if (slots.contains(currentSlot)) currentSlot else slots.firstOrNull() ?: TimeSlot.NONE

        viewModel.initPending()

        TimeRangeBottomSheet(
            context       = this,
            items         = slots,
            initialSlot   = initialSlot,
            onSlotChanged = { slot -> viewModel.selectPending(slot) },
            onConfirm     = { slot ->
                viewModel.confirmSelection()
                Toast.makeText(this, "Đã chọn: ${slot.label} (${slot.display})", Toast.LENGTH_SHORT).show()
            },
            onCancel      = { /* pending discarded, confirmed unchanged */ }
        ).show()
    }
}
