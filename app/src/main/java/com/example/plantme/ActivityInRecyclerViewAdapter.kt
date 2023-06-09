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

class ActivityInRecyclerViewAdapter(
    var activities: List<Int>,
    ): RecyclerView.Adapter<ActivityInRecyclerViewAdapter.ActivityInRecyclerViewHolder>() {

        inner class ActivityInRecyclerViewHolder(val binding: ItemActivityInRecyclerViewOverviewBinding): RecyclerView.ViewHolder(binding.root) {

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityInRecyclerViewHolder{
            val dao = parent.context?.let { Databse.getInstance(it).createDao() }
            val binding =  ItemActivityInRecyclerViewOverviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            binding.pictogram.setOnClickListener {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:MM:SS.SSS")
                val date = LocalDateTime.now().format(formatter)
                val act = CareActivity(date,"XXXX", 1)
                dao?.let { it1 -> this.saveActivityInDatabase(it1, act) }

            }
                //parent.findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            return ActivityInRecyclerViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ActivityInRecyclerViewHolder, position: Int) {

            with(holder)
            {
                when (activities[position]) {
                    1 -> {binding.tvActivity.text = "Watering"
                        binding.tvInfoAboutActivity.text
                        binding.pictogram.setImageResource(R.drawable.water) }
                    2 -> {binding.tvActivity.text = "Watering"
                        binding.tvInfoAboutActivity.text
                        binding.pictogram.setImageResource(R.drawable.fertilise)}
                    3 -> {binding.tvActivity.text = "Watering"
                        binding.tvInfoAboutActivity.text
                        binding.pictogram.setImageResource(R.drawable.repot)}
                    4 -> {binding.tvActivity.text = "Watering"
                        binding.tvInfoAboutActivity.text
                        binding.pictogram.setImageResource(R.drawable.clean)}
                    else -> {binding.tvActivity.text = "Watering"
                        binding.tvInfoAboutActivity.text
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







}
