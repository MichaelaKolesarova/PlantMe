package com.example.plantme.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.plantme.data.entities.CareActivity
import com.example.plantme.data.entities.Flower

@Database(
    entities = [
        Flower::class,
        CareActivity::class],
    version = 1,)

/**
 * Class Database representation Copying pre-filled database  using Room
 * One instance - singelton of database and dao is created
 */
abstract class Databse: RoomDatabase() {
    abstract fun  createDao(): MyDao

    companion object {
        @Volatile
        private var INSTANCE: Databse? = null

        fun getInstance(context: Context): Databse {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext, Databse::class.java,"Database.db")
                    .createFromAsset("Database.db").build()
                    .also { INSTANCE = it}
            }
        }
    }

}