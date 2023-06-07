package com.example.plantme.data.entities

import android.graphics.Picture
import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity(tableName = "CareActivity", primaryKeys = ["name", "date"])

data class CareActivity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "activitytype") val activitytype: Int
)
