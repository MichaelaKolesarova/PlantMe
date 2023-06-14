package com.example.plantme.data

import androidx.room.*
import com.example.plantme.data.entities.CareActivity
import com.example.plantme.data.entities.Flower

/**
 * Query library to communicate with the database to avoid using string messages
 */
@Dao
interface MyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewFlower(flower: Flower)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewActivity(activity: CareActivity)

    @Query("SELECT * FROM Flower")
    suspend fun getAllFlowers(): List<Flower>

    @Query("SELECT * FROM CareActivity WHERE name = :name AND activitytype = :type")
    suspend fun getSpecificActivities(name:String, type:Int): List<CareActivity>

    @Query("SELECT * FROM Flower WHERE name = :pk")
    suspend fun getSpecificFlower(pk: String): Flower


}
