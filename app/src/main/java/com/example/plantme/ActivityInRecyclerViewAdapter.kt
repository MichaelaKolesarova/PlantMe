package com.example.plantme

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantme.data.Databse
import com.example.plantme.data.MyDao
import com.example.plantme.data.entities.CareActivity
import com.example.plantme.data.entities.Flower
import com.example.plantme.databinding.ItemActivityInRecyclerViewOverviewBinding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import kotlinx.coroutines.runBlocking
import java.time.temporal.ChronoUnit
import kotlin.time.Duration


class ActivityInRecyclerViewAdapter(
    var activities: List<Int>, name: String): RecyclerView.Adapter<ActivityInRecyclerViewAdapter.ActivityInRecyclerViewHolder>() {
        val name = name

        inner class ActivityInRecyclerViewHolder(val binding: ItemActivityInRecyclerViewOverviewBinding): RecyclerView.ViewHolder(binding.root) {

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityInRecyclerViewHolder{
            val dao = parent.context?.let { Databse.getInstance(it).createDao() }
            val binding =  ItemActivityInRecyclerViewOverviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            


            return ActivityInRecyclerViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ActivityInRecyclerViewHolder, position: Int) {
            val dao = holder.itemView.context?.let { Databse.getInstance(it).createDao() }

            with(holder)
            {
                when (activities[position]) {
                    1 -> {binding.tvActivity.text = "Polievanie"
                        binding.pictogram.setImageResource(R.drawable.water)
                        dao?.let { getTextForInfo(it,1, binding.tvInfoAboutActivity) }
                        binding.pictogram.setOnClickListener {
                            val act = CareActivity(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), name, 1)
                            runBlocking {
                                dao?.insertNewActivity(act)}}}
                    2 -> {binding.tvActivity.text = "Hnojenie"
                        binding.pictogram.setImageResource(R.drawable.fertilise)
                        dao?.let { getTextForInfo(it,2, binding.tvInfoAboutActivity) }
                        binding.pictogram.setOnClickListener {
                            val act = CareActivity(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), name, 2)
                            runBlocking {
                                dao?.insertNewActivity(act)}}}
                    3 -> {binding.tvActivity.text = "Presádzanie"
                        binding.pictogram.setImageResource(R.drawable.repot)
                        dao?.let { getTextForInfo(it,3, binding.tvInfoAboutActivity) }
                        binding.pictogram.setOnClickListener {
                            val act = CareActivity(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), name, 3)
                            runBlocking {
                                dao?.insertNewActivity(act)}}}
                    4 -> {binding.tvActivity.text = "Čistenie"
                        binding.pictogram.setImageResource(R.drawable.clean)
                        dao?.let { getTextForInfo(it,4, binding.tvInfoAboutActivity) }
                        binding.pictogram.setOnClickListener {
                            val act = CareActivity(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), name, 4)
                            runBlocking {
                                dao?.insertNewActivity(act)}}
                    }
                }
            }

        }


        override fun getItemCount(): Int {
            return activities.size
        }

    private fun saveActivityInDatabase(dao: MyDao, activity: CareActivity)
    {

        runBlocking {
            dao.insertNewActivity(activity)
        }
    }
    
    private fun getdaysToDoActivityAgain(dao: MyDao, type: Int): Int
    {

        return runBlocking {
            val acts = dao.getSpecificActivities(name, type)
            if (acts.isNotEmpty())
            {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val latestActivity = acts.maxBy { it.date }
                val activityDate = LocalDateTime.parse(latestActivity.date, formatter)
                val currentDate = LocalDateTime.now()
                if (activityDate > currentDate)
                {
                    return@runBlocking ChronoUnit.DAYS.between(currentDate, activityDate).toInt()
                } else {
                    return@runBlocking ChronoUnit.DAYS.between(currentDate, activityDate).toInt().inv()
                }
            }
            else
            {
                return@runBlocking 0
            }

        }

    }

    @SuppressLint("ResourceAsColor")
    private fun getTextForInfo(dao: MyDao, type: Int, textView: TextView )
    {

        runBlocking {
            var frequency = 1
            var helpTextBefore = " do ďalšieho "
            var helpTextAfter = " dní pozadu "
            var helpTextToday = "o všetko postarné"
            when (type) {
                1 -> { frequency = dao.getSpecificFlower(name).cleaning_frequency
                    helpTextBefore = helpTextBefore + "zalievania"}
                2 -> { frequency = dao.getSpecificFlower(name).fertilize_frequency
                    helpTextBefore = helpTextBefore + "hnojenia"}
                3 -> { frequency = dao.getSpecificFlower(name).repoting_frequency
                    helpTextBefore = helpTextBefore + "presádzania"}
                4 -> { frequency = dao.getSpecificFlower(name).cleaning_frequency
                    helpTextBefore = helpTextBefore + "čistenia"}
            }

            val daysToActivity = frequency - getdaysToDoActivityAgain(dao, type)
            if (daysToActivity > 0) {
                if (daysToActivity == 0){
                    textView.text = helpTextToday
                }
                else if (daysToActivity == 1){
                    textView.text = daysToActivity.toString()+ " deň" + helpTextBefore
                } else if (daysToActivity == 2) {
                    textView.text = daysToActivity.toString() + " dni"+ helpTextBefore
                } else if (daysToActivity > 0) {
                    textView.text = daysToActivity.toString() + " dní" + helpTextBefore
                }
            } else {
                if (daysToActivity > -1){
                    textView.text = daysToActivity.inv().toString() + helpTextAfter
                }
                else
                {
                    textView.text = daysToActivity.inv().toString() + helpTextAfter
                }
                textView.setTextColor(Color.RED)
            }

        }

    }









}
