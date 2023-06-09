package com.example.plantme.data

import androidx.room.*
import com.example.plantme.data.entities.CareActivity
import com.example.plantme.data.entities.Flower

@Dao
interface MyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewFlower(flower: Flower)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewActivity(activity: CareActivity)

    @Query("SELECT * FROM Flower")
    suspend fun getAllFlowers(): List<Flower>

    @Query("SELECT * FROM Flower WHERE name = :pk")
    suspend fun getSpecificFlower(pk: String): Flower


}
