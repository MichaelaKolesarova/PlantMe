package com.example.plantme.data

import androidx.room.*
import com.example.plantme.data.entities.Flower

@Dao
interface MyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewFlower(flower: Flower)

    @Query("SELECT * FROM Flower")
    suspend fun getAllFlowers(): List<Flower>


}
