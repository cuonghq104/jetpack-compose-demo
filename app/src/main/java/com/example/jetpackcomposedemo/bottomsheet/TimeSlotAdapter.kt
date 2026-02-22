package com.example.jetpackcomposedemo.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackcomposedemo.databinding.ItemTimeSlotBinding

class TimeSlotAdapter(
    private val items: List<TimeSlot>,
    initialSelectedPosition: Int = 0,
    private val onSlotSelected: (TimeSlot) -> Unit
) : RecyclerView.Adapter<TimeSlotAdapter.ViewHolder>() {

    private var selectedPosition: Int = initialSelectedPosition

    fun updateSelected(position: Int) {
        if (position == selectedPosition) return
        val previous = selectedPosition
        selectedPosition = position
        notifyItemChanged(previous)
        notifyItemChanged(selectedPosition)
        onSlotSelected(items[selectedPosition])
    }

    inner class ViewHolder(private val binding: ItemTimeSlotBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(slot: TimeSlot, isSelected: Boolean) {
            binding.tvTimeDisplay.text = slot.display
            binding.root.isSelected = isSelected
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTimeSlotBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position == selectedPosition)
    }

    override fun getItemCount(): Int = items.size
}
