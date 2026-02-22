package com.example.jetpackcomposedemo.bottomsheet

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ListView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.facebook.shimmer.ShimmerFrameLayout
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.databinding.DialogDatePickerBinding
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class DatePickerDialog(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private val uiState: LiveData<DatePickerUiState>,
    private val initialDate: LocalDate? = null,
    private val onConfirm: (LocalDate) -> Unit
) {
    private val headerFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
    private val today = LocalDate.now()

    private val availableMonths: List<YearMonth> = (0..3).map {
        YearMonth.from(today).plusMonths(it.toLong())
    }

    private var displayedMonth: YearMonth = YearMonth.from(initialDate ?: today)
    private var selectedDate: LocalDate? = initialDate
    private var unavailableDates: Set<LocalDate> = emptySet()

    private var monthPopup: PopupWindow? = null

    fun show() {
        val dialog = Dialog(context)
        val binding = DialogDatePickerBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        setupMonthDropdown(binding)
        observeUiState(binding, dialog)

        binding.btnPrevMonth.setOnClickListener {
            val prev = displayedMonth.minusMonths(1)
            if (availableMonths.contains(prev)) {
                displayedMonth = prev
                renderMonth(binding)
            }
        }

        binding.btnNextMonth.setOnClickListener {
            val next = displayedMonth.plusMonths(1)
            if (availableMonths.contains(next)) {
                displayedMonth = next
                renderMonth(binding)
            }
        }

        binding.btnCancel.setOnClickListener { dialog.dismiss() }

        binding.btnConfirm.setOnClickListener {
            selectedDate?.let { onConfirm(it) }
            dialog.dismiss()
        }

        updateConfirmState(binding)
        dialog.show()
    }

    // ── UI state → shimmer / calendar ───────────────────────────────────────

    private fun observeUiState(binding: DialogDatePickerBinding, dialog: Dialog) {
        uiState.observe(lifecycleOwner) { state ->
            if (!dialog.isShowing) return@observe
            when (state) {
                is DatePickerUiState.Loading -> {
                    binding.viewShimmer.visibility = View.VISIBLE
                    (binding.viewShimmer as ShimmerFrameLayout).apply {
                        setShimmer(
                            com.facebook.shimmer.Shimmer.AlphaHighlightBuilder()
                                .setDuration(1200)
                                .setBaseAlpha(0.9f)
                                .setHighlightAlpha(0.6f)
                                .setDirection(com.facebook.shimmer.Shimmer.Direction.LEFT_TO_RIGHT)
                                .setAutoStart(true)
                                .build()
                        )
                        startShimmer()
                    }
                    binding.viewCalendar.visibility = View.GONE
                    binding.btnPrevMonth.visibility = View.INVISIBLE
                    binding.btnNextMonth.visibility = View.INVISIBLE
                    binding.layoutMonthDropdown.visibility = View.INVISIBLE
                    binding.btnConfirm.isEnabled = false
                }
                is DatePickerUiState.GetDateSuccess -> {
                    unavailableDates = state.unavailableDates

                    // Clear selection if the previously selected date is now unavailable
                    if (selectedDate != null && selectedDate in unavailableDates) {
                        selectedDate = null
                    }

                    (binding.viewShimmer as ShimmerFrameLayout).stopShimmer()
                    binding.viewShimmer.visibility = View.GONE
                    binding.viewCalendar.visibility = View.VISIBLE
                    binding.btnPrevMonth.visibility = View.VISIBLE
                    binding.btnNextMonth.visibility = View.VISIBLE
                    binding.layoutMonthDropdown.visibility = View.VISIBLE

                    renderMonth(binding)
                    updateConfirmState(binding)
                }
            }
        }
    }

    // ── Month popup ──────────────────────────────────────────────────────────

    private fun setupMonthDropdown(binding: DialogDatePickerBinding) {
        binding.layoutMonthDropdown.setOnClickListener { anchor ->
            if (monthPopup?.isShowing == true) {
                monthPopup?.dismiss()
                return@setOnClickListener
            }
            showMonthPopup(anchor, binding)
        }
    }

    private fun showMonthPopup(anchor: View, binding: DialogDatePickerBinding) {
        val listView = ListView(context).apply {
            divider = ColorDrawable(Color.parseColor("#1F000000"))
            dividerHeight = 1
        }

        val labels = availableMonths.map { it.format(headerFormatter) }
        val adapter = object : android.widget.ArrayAdapter<String>(
            context, R.layout.item_month_option, R.id.tvMonthOption, labels
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent)
                val tv = v.findViewById<TextView>(R.id.tvMonthOption)
                val isSelected = availableMonths[position] == displayedMonth
                tv.setTextColor(if (isSelected) Color.parseColor("#6200EE") else Color.BLACK)
                tv.setTypeface(null, if (isSelected) android.graphics.Typeface.BOLD else android.graphics.Typeface.NORMAL)
                return v
            }
        }
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            displayedMonth = availableMonths[position]
            monthPopup?.dismiss()
            renderMonth(binding)
        }

        val itemHeightPx = (48 * context.resources.displayMetrics.density).toInt()
        val popupHeight  = itemHeightPx * labels.size

        val popup = PopupWindow(
            listView,
            anchor.width.coerceAtLeast((120 * context.resources.displayMetrics.density).toInt()),
            popupHeight,
            true
        ).apply {
            setBackgroundDrawable(ColorDrawable(Color.WHITE))
            elevation = 8f * context.resources.displayMetrics.density
            isOutsideTouchable = true
            setOnDismissListener {
                binding.ivDropdownArrow.rotation = 0f
                monthPopup = null
            }
        }

        binding.ivDropdownArrow.rotation = 180f
        popup.showAsDropDown(anchor, 0, 0, Gravity.START)
        monthPopup = popup
    }

    // ── Calendar grid ────────────────────────────────────────────────────────

    private fun renderMonth(binding: DialogDatePickerBinding) {
        binding.tvCurrentMonth.text = displayedMonth.format(headerFormatter)

        binding.btnPrevMonth.alpha =
            if (availableMonths.contains(displayedMonth.minusMonths(1))) 1f else 0.3f
        binding.btnNextMonth.alpha =
            if (availableMonths.contains(displayedMonth.plusMonths(1))) 1f else 0.3f

        val cells = buildCells()
        val adapter = DateCellAdapter(
            context        = context,
            cells          = cells,
            selectedDate   = selectedDate,
            unavailableDates = unavailableDates
        ) { date ->
            selectedDate = date
            updateConfirmState(binding)
            renderMonth(binding)
        }
        binding.gridDates.adapter = adapter
    }

    private fun buildCells(): List<LocalDate?> {
        val firstDay    = displayedMonth.atDay(1)
        val startOffset = firstDay.dayOfWeek.value % 7   // Sun=0 … Sat=6
        val cells       = mutableListOf<LocalDate?>()
        repeat(startOffset) { cells.add(null) }
        for (d in 1..displayedMonth.lengthOfMonth()) cells.add(displayedMonth.atDay(d))
        while (cells.size < 42) cells.add(null)
        return cells
    }

    private fun updateConfirmState(binding: DialogDatePickerBinding) {
        binding.btnConfirm.isEnabled = selectedDate != null
    }

    // ── Date cell adapter ────────────────────────────────────────────────────

    private class DateCellAdapter(
        context: Context,
        private val cells: List<LocalDate?>,
        private val selectedDate: LocalDate?,
        private val unavailableDates: Set<LocalDate>,
        private val onDateClick: (LocalDate) -> Unit
    ) : android.widget.BaseAdapter() {

        private val inflater = LayoutInflater.from(context)
        private val today    = LocalDate.now()

        private val colorUnavailable = Color.parseColor("#BDBDBD")
        private val colorPrimary     = Color.parseColor("#6200EE")

        override fun getCount()          = cells.size
        override fun getItem(pos: Int)   = cells[pos]
        override fun getItemId(pos: Int) = pos.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val root = (convertView ?: inflater.inflate(
                R.layout.item_date_cell, parent, false
            )) as FrameLayout

            val circle = root.findViewById<View>(R.id.viewCircle)
            val tv     = root.findViewById<TextView>(R.id.tvDay)
            val date   = cells[position]

            if (date == null) {
                tv.text           = ""
                tv.setTextColor(Color.TRANSPARENT)
                circle.visibility = View.GONE
                root.isClickable  = false
            } else {
                tv.text = date.dayOfMonth.toString()

                val isUnavailable = date in unavailableDates

                if (isUnavailable) {
                    circle.visibility = View.GONE
                    tv.setTextColor(colorUnavailable)
                    tv.alpha      = 0.4f
                    root.isClickable = false
                    root.setOnClickListener(null)
                } else {
                    tv.alpha      = 1f
                    root.isClickable = true

                    when {
                        date == selectedDate -> {
                            circle.visibility = View.VISIBLE
                            tv.setTextColor(Color.WHITE)
                        }
                        date == today -> {
                            circle.visibility = View.GONE
                            tv.setTextColor(colorPrimary)
                        }
                        else -> {
                            circle.visibility = View.GONE
                            tv.setTextColor(Color.BLACK)
                        }
                    }

                    root.setOnClickListener { onDateClick(date) }
                }
            }

            return root
        }
    }
}
