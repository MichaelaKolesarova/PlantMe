package com.example.plantme

import android.graphics.Color
import android.widget.TextView
import com.example.plantme.data.MyDao
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class TextInTextViewWithInfoSlovakAdapter {
    private fun getdaysToDoActivityAgain(dao: MyDao, name: String, type:Int): Int
    {
        return runBlocking {
            val acts = dao.getSpecificActivities(name, type)
            if (acts.isNotEmpty())
            {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val latestActivity = acts.maxBy { it.date }
                val activityDate = LocalDateTime.parse(latestActivity.date, formatter)
                val currentDate = LocalDateTime.now()

                return@runBlocking ChronoUnit.DAYS.between(currentDate, activityDate).toInt()
            }
            else
            {
                return@runBlocking 0
            }
        }
    }

    public fun getTextForInfo(dao: MyDao, textView: TextView, name:String, type: Int)
    {runBlocking {
        var frequency = 0
        var helpTextBefore = " do ďalšieho "
        var helpTextAfter = " pozadu "
        when (type) {
            1 -> {
                frequency = dao.getSpecificFlower(name).watering_frequency
                helpTextBefore = helpTextBefore + "zalievania"
            }
            2 -> {
                frequency = dao.getSpecificFlower(name).fertilize_frequency
                helpTextBefore = helpTextBefore + "hnojenia"
            }
            3 -> {
                frequency = dao.getSpecificFlower(name).repoting_frequency
                helpTextBefore = helpTextBefore + "presádzania"
            }
            4 -> {
                frequency = dao.getSpecificFlower(name).cleaning_frequency
                helpTextBefore = helpTextBefore + "čistenia"
            }
        }

        var daysToActivity = frequency + getdaysToDoActivityAgain(dao, name, type)
        if (daysToActivity >= 0) {
            if (daysToActivity == 0) {
                textView.text = "dnes je nutné sa postarať"
            } else if (daysToActivity == 1) {
                textView.text = daysToActivity.toString() + " deň" + helpTextBefore
            } else if (daysToActivity >= 2 && daysToActivity <= 4) {
                textView.text = daysToActivity.toString() + " dni" + helpTextBefore
            } else {
                textView.text = daysToActivity.toString() + " dní" + helpTextBefore
            }
        } else {
            daysToActivity++
            if (daysToActivity == -1) {
                textView.text = (-1 * daysToActivity).toString() + " deň" + helpTextAfter
            } else if (daysToActivity <= -2 && daysToActivity >= -4) {
                textView.text = (-1 * daysToActivity).toString() + " dni" + helpTextAfter
            } else if (daysToActivity == 0) {
                textView.text = "dnes je nutné sa postarať"
            } else {
                textView.text = (-1 * daysToActivity).toString() + helpTextAfter
            }
            textView.setTextColor(Color.RED)
        }

    }
    }
}