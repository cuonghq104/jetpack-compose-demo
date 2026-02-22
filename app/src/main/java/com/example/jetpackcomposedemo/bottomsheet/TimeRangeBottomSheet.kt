package com.example.jetpackcomposedemo.bottomsheet

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackcomposedemo.databinding.BottomSheetLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class TimeRangeBottomSheet(
    private val context: Context,
    private val items: List<TimeSlot>,
    private val initialSlot: TimeSlot,
    private val onSlotChanged: (TimeSlot) -> Unit,
    private val onConfirm: (TimeSlot) -> Unit,
    private val onCancel: () -> Unit
) {
    companion object {
        private const val ITEM_HEIGHT_DP = 56
        private const val SHEET_HEIGHT_RATIO = 2       // 1/2 of screen
        private const val GRADIENT_FADE_HEIGHT_DP = 80
    }

    private val density = context.resources.displayMetrics.density
    private val itemHeightPx = (ITEM_HEIGHT_DP * density).toInt()

    // Tracks the slot currently snapped in the wheel
    private var currentSlot: TimeSlot = initialSlot

    fun show() {
        val dialog = BottomSheetDialog(context)
        val binding = BottomSheetLayoutBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)

        val initialIndex = items.indexOf(initialSlot).coerceAtLeast(0)

        setupRecyclerView(binding, initialIndex)
        setupButtons(dialog, binding)
        setupSheetHeight(dialog, binding, initialIndex)

        dialog.show()
    }

    private fun setupRecyclerView(binding: BottomSheetLayoutBinding, initialIndex: Int) {
        val layoutManager = LinearLayoutManager(context)
        binding.rvTimeSlots.layoutManager = layoutManager
        binding.rvTimeSlots.isNestedScrollingEnabled = false

        val adapter = TimeSlotAdapter(
            items = items,
            initialSelectedPosition = initialIndex
        ) { slot ->
            currentSlot = slot
            onSlotChanged(slot)
            binding.btnConfirm.isEnabled = slot != TimeSlot.NONE
        }
        binding.rvTimeSlots.adapter = adapter

        // Initialise button state to match the initial slot
        binding.btnConfirm.isEnabled = initialSlot != TimeSlot.NONE

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvTimeSlots)

        binding.rvTimeSlots.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val snappedView = snapHelper.findSnapView(layoutManager) ?: return
                    val position = layoutManager.getPosition(snappedView)
                    if (position >= 0) adapter.updateSelected(position)
                }
            }
        })

        // Store layoutManager + initialIndex for setupSheetHeight
        binding.rvTimeSlots.tag = Pair(layoutManager, initialIndex)
    }

    private fun setupButtons(dialog: BottomSheetDialog, binding: BottomSheetLayoutBinding) {
        binding.btnConfirm.setOnClickListener {
            onConfirm(currentSlot)
            dialog.dismiss()
        }
        binding.btnCancel.setOnClickListener {
            onCancel()
            dialog.dismiss()
        }
    }

    private fun setupSheetHeight(
        dialog: BottomSheetDialog,
        binding: BottomSheetLayoutBinding,
        initialIndex: Int
    ) {
        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<FrameLayout>(
                com.google.android.material.R.id.design_bottom_sheet
            ) ?: return@setOnShowListener

            val screenHeight = context.resources.displayMetrics.heightPixels
            val sheetHeight = screenHeight / SHEET_HEIGHT_RATIO

            bottomSheet.layoutParams = bottomSheet.layoutParams.apply {
                height = sheetHeight
            }

            BottomSheetBehavior.from(bottomSheet).apply {
                peekHeight = sheetHeight
                state = BottomSheetBehavior.STATE_EXPANDED
                isHideable = false
                isDraggable = false
            }

            binding.rvTimeSlots.post {
                val sidePadding = (binding.rvTimeSlots.height - itemHeightPx) / 2

                binding.rvTimeSlots.setPadding(0, sidePadding, 0, sidePadding)
                binding.rvTimeSlots.clipToPadding = false

                binding.rvTimeSlots.post {
                    (binding.rvTimeSlots.layoutManager as? LinearLayoutManager)
                        ?.scrollToPositionWithOffset(0, 0)
                    binding.rvTimeSlots.post {
                        binding.rvTimeSlots.scrollBy(0, initialIndex * itemHeightPx)
                    }
                }
            }
        }
    }
}
