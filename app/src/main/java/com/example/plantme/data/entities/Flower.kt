package com.example.plantme.data.entities


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class represneting a flower, which is a content of the table "flower" in database Database.db
 * variable Picture is not manadatory
 * name is the primary key of the Flower
 */
@Entity(tableName = "Flower")
data class Flower(
    @PrimaryKey val name: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "picture") var picture: ByteArray?,
    @ColumnInfo(name = "watering_frequency") val watering_frequency: Int,
    @ColumnInfo(name = "fertilize_frequency") val fertilize_frequency: Int,
    @ColumnInfo(name = "repoting_frequency") val repoting_frequency: Int,
    @ColumnInfo(name = "cleaning_frequency") val cleaning_frequency: Int
    )
