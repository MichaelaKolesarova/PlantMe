package com.example.plantme

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plantme.data.entities.Flower
import com.example.plantme.databinding.ItemActivityInRecyclerViewOverviewBinding

class ActivityInRecyclerViewAdapter(
    var activities: List<Int>,
    ): RecyclerView.Adapter<ActivityInRecyclerViewAdapter.ActivityInRecyclerViewHolder>() {

        inner class ActivityInRecyclerViewHolder(val binding: ItemActivityInRecyclerViewOverviewBinding): RecyclerView.ViewHolder(binding.root) {}

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityInRecyclerViewHolder{

            val binding =  ItemActivityInRecyclerViewOverviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            binding.itemInRecyclerViewOverview.setOnClickListener {
                //parent.findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
            return ActivityInRecyclerViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ActivityInRecyclerViewHolder, position: Int) {
            with(holder)
            {
                when (activities[position]) {
                    1 -> {binding.tvActivity.text = "Watering"
                        binding.tvInfoAboutActivity.text
                        binding.pictogram.setImageResource(R.drawable.water)}
                    2 -> {binding.tvActivity.text = "Fertilize"
                        binding.tvInfoAboutActivity.text
                        binding.pictogram.setImageResource(R.drawable.fertilise)}
                    3 -> {binding.tvActivity.text = "Repoting"
                        binding.tvInfoAboutActivity.text
                        binding.pictogram.setImageResource(R.drawable.repot)}
                    4 -> {binding.tvActivity.text = "Clean"
                        binding.tvInfoAboutActivity.text
                        binding.pictogram.setImageResource(R.drawable.clean)}
                    else -> {binding.tvActivity.text = "Watering"
                        binding.tvInfoAboutActivity.text
                        binding.pictogram.setImageResource(R.drawable.clean)}
                }


            }

        }

        override fun getItemCount(): Int {
            return activities.size
        }
}