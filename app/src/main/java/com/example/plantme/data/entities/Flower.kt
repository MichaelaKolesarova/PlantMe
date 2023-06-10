package com.example.plantme.data.entities

import android.graphics.Bitmap
import android.graphics.Picture
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob

@Entity(tableName = "Flower")
data class Flower(
    @PrimaryKey val name: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "picture", typeAffinity = ColumnInfo.BLOB) val picture: ByteArray?,
    @ColumnInfo(name = "watering_frequency") val watering_frequency: Int,
    @ColumnInfo(name = "fertilize_frequency") val fertilize_frequency: Int,
    @ColumnInfo(name = "repoting_frequency") val repoting_frequency: Int,
    @ColumnInfo(name = "cleaning_frequency") val cleaning_frequency: Int
    )
