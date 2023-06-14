package com.example.plantme.data.entities


import androidx.room.ColumnInfo
import androidx.room.Entity

/**
 * Data class represneting a an activity of the person, takong care of
 * specific flower (distinguished by name)
 * in specific tame (distinguished by date)
 * and doing specific activity (1 - watering, 2 - fertilizing, 3 - repoting, 4 - cleaning)
 * content of the table "CareActivity" in database Database.db
 * all of the atributes are the primary key of the Flower
 */
@Entity(tableName = "CareActivity", primaryKeys = ["name", "date", "activitytype"])

data class CareActivity(
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "activitytype") val activitytype: Int
)
