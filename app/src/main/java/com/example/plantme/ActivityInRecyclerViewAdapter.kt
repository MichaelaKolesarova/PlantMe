package com.example.plantme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

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

            when (binding.tvActivity.text) {
                "Watering" -> {binding.tvInfoAboutActivity.text =
                    dao?.let { getdaysToDoActivityAgain(it, name, 1).toString() }
                }
                "Fertilizing" -> {binding.tvInfoAboutActivity.text =
                    dao?.let { getdaysToDoActivityAgain(it, name, 2).toString() }
                }
                "Repoting" -> {binding.tvInfoAboutActivity.text =
                    dao?.let { getdaysToDoActivityAgain(it, name, 3).toString() }
                }
                "Cleaning" -> {binding.tvInfoAboutActivity.text =
                    dao?.let { getdaysToDoActivityAgain(it, name, 4).toString() }
                }}

            binding.pictogram.setOnClickListener {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:MM:SS.SSS")
                val date = LocalDateTime.now().format(formatter)
                var type = 1
                when (binding.tvActivity.text) {
                    "Watering" -> {type = 1 }
                    "Fertilizing" -> {type = 2 }
                    "Repoting" -> {type = 3 }
                    "Cleaning" -> {type = 4 }}
                val act = CareActivity(date, name, type)

                dao?.let { it1 -> saveActivityInDatabase(it1, act) }

            }
                //parent.findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            return ActivityInRecyclerViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ActivityInRecyclerViewHolder, position: Int) {

            with(holder)
            {
                when (activities[position]) {
                    1 -> {binding.tvActivity.text = "Watering"
                        binding.pictogram.setImageResource(R.drawable.water) }
                    2 -> {binding.tvActivity.text = "Fertilizing"
                        binding.tvInfoAboutActivity.text
                        binding.pictogram.setImageResource(R.drawable.fertilise)}
                    3 -> {binding.tvActivity.text = "Repoting"

                        binding.pictogram.setImageResource(R.drawable.repot)}
                    4 -> {binding.tvActivity.text = "Cleaning"

                        binding.pictogram.setImageResource(R.drawable.clean)}
                    else -> {binding.tvActivity.text = "Watering"

                        binding.pictogram.setImageResource(R.drawable.water)}
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

    private fun getdaysToDoActivityAgain(dao: MyDao, name: String, type: Int): Int
    {
        return runBlocking {
            val lastActivity = dao.getSpecificActivities(name, type)[0]
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:MM:SS.SSS")
            val activityDate = LocalDateTime.parse(lastActivity.date, formatter)
            val currentDate = LocalDateTime.now()
            return@runBlocking ChronoUnit.DAYS.between(currentDate, activityDate).toInt()
        }

    }









}
