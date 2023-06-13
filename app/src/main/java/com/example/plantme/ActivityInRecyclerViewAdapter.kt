package com.example.plantme

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.plantme.data.Databse
import com.example.plantme.data.MyDao
import com.example.plantme.data.entities.CareActivity
import com.example.plantme.databinding.ItemActivityInRecyclerViewOverviewBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.runBlocking
import java.time.temporal.ChronoUnit


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
                            runBlocking { dao?.insertNewActivity(act)}}
                        binding.tvInfoAboutActivity.invalidate()}

                    2 -> {binding.tvActivity.text = "Hnojenie"
                        binding.pictogram.setImageResource(R.drawable.fertilise)
                        dao?.let { getTextForInfo(it,2, binding.tvInfoAboutActivity) }
                        binding.pictogram.setOnClickListener {
                            val act = CareActivity(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), name, 2)
                            runBlocking {
                                dao?.insertNewActivity(act)}}
                        binding.tvInfoAboutActivity.invalidate()}
                    3 -> {binding.tvActivity.text = "Presádzanie"
                        binding.pictogram.setImageResource(R.drawable.repot)
                        dao?.let { getTextForInfo(it,3, binding.tvInfoAboutActivity) }
                        binding.pictogram.setOnClickListener {
                            val act = CareActivity(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), name, 3)
                            runBlocking {
                                dao?.insertNewActivity(act)}}
                        binding.tvInfoAboutActivity.invalidate()}
                    4 -> {binding.tvActivity.text = "Čistenie"
                        binding.pictogram.setImageResource(R.drawable.clean)
                        dao?.let { getTextForInfo(it,4, binding.tvInfoAboutActivity) }
                        binding.pictogram.setOnClickListener {
                            val act = CareActivity(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), name, 4)
                            runBlocking {
                                dao?.insertNewActivity(act)}
                            notifyDataSetChanged()}

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
    
    private fun getDaysBetween(dao: MyDao, type: Int): Int
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

    @SuppressLint("ResourceAsColor")
    private fun getTextForInfo(dao: MyDao, type: Int, textView: TextView ) {

        runBlocking {
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

            var daysToActivity = frequency + getDaysBetween(dao, type)
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
