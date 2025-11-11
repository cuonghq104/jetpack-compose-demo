package com.example.jetpackcomposedemo.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Type:
 * 1: Essential
 * 2: Shopping & Entertainment
 * 3: Education, Health
 * 4: Misc
 */
@Entity(tableName = "tbl_spend_category")
data class SpendCategory(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "type") val type: Int,
)