package com.example.plantme

import android.content.Context
import android.content.res.Resources
import android.graphics.Color

import android.widget.TextView
import com.example.plantme.data.MyDao

import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
/**
 * Class communicating with database to create right text in right dorm about next time to do CareActivity
 */
class TextInTextViewWithInfoSlovakAdapter {
    /**
     * function counts the difference between current date and the date when ws the activity done the last tme
     */
     fun getdaysToDoActivityAgain(dao: MyDao, name: String, type:Int): Int
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

    /**
     * function counts how many days until the activity should be performed again and
     * if daysToGetActivity is negative (means we should have already done the activity, text is Red
     * creates the Slovak sentence
     */
     fun getTextForInfo(dao: MyDao, textView: TextView, name:String, type: Int, context: Context)

    {runBlocking {
        var frequency = 0
        var helpTextBefore:String = context.resources.getString(R.string.upToNext).toString()
        val helpTextAfter = context.resources.getString(R.string.ahead)
        when (type) {
            1 -> {
                frequency = dao.getSpecificFlower(name).watering_frequency
                helpTextBefore += context.resources.getString(R.string.watering)
            }
            2 -> {
                frequency = dao.getSpecificFlower(name).fertilize_frequency
                helpTextBefore += context.resources.getString(R.string.fertilizing)
            }
            3 -> {
                frequency = dao.getSpecificFlower(name).repoting_frequency
                helpTextBefore += context.resources.getString(R.string.repoting)
            }
            4 -> {
                frequency = dao.getSpecificFlower(name).cleaning_frequency
                helpTextBefore += context.resources.getString(R.string.cleaning)
            }
        }

        var daysToActivity = frequency + getdaysToDoActivityAgain(dao, name, type)
        if (daysToActivity >= 0) {
            if (daysToActivity == 0) {
                textView.text = context.resources.getString(R.string.todayIsNeededToBeTakenCare)
            } else if (daysToActivity == 1) {
                textView.text = daysToActivity.toString() + context.resources.getString(R.string.one_day) + helpTextBefore
            } else if (daysToActivity >= 2 && daysToActivity <= 4) {
                textView.setText(daysToActivity.toString() +context.resources.getString(R.string.two_to_four_days) + helpTextBefore)
            } else {
                textView.text = daysToActivity.toString() + context.resources.getString(R.string.more_than_five_days) + helpTextBefore
            }
        } else {
            daysToActivity++
            if (daysToActivity == -1) {
                textView.text = (-1 * daysToActivity).toString() + context.resources.getString(R.string.one_day) + helpTextAfter
            } else if (daysToActivity <= -2 && daysToActivity >= -4) {
                textView.text = (-1 * daysToActivity).toString() + context.resources.getString(R.string.two_to_four_days) + helpTextAfter
            } else if (daysToActivity == 0) {
                textView.text = Resources.getSystem().getString(R.string.todayIsNeededToBeTakenCare)
            } else {
                textView.text = (-1 * daysToActivity).toString() + helpTextAfter
            }
            textView.setTextColor(Color.RED)
        }

    }
    }
}