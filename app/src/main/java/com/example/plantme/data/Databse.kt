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
        CareActivity::class
    ],
    version = 1,
)
abstract class Databse: RoomDatabase() {
    abstract fun  createDao(): MyDao

    companion object {
        @Volatile
        private var INSTANCE: Databse? = null

        fun getInstance(context: Context): Databse {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext, Databse::class.java,"database.db")
                    .createFromAsset("Database.db").build()
                    .also { INSTANCE = it}
            }
        }
    }

}