package com.example.plantme.data.entities

import android.graphics.Picture
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Flower")
data class Flower(
    @PrimaryKey val name: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "picture") val picture: Picture,
    @ColumnInfo(name = "watering_frequency") val watering_frequency: Int,
    @ColumnInfo(name = "fertilize_frequency") val fertilize_frequency: Int,
    @ColumnInfo(name = "repoting_frequency") val repoting_frequency: Int,
    @ColumnInfo(name = "cleaning_frequency") val cleaning_frequency: Int
    )
