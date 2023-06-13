package com.example.plantme.adapters

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.plantme.R
import com.example.plantme.TextInTextViewWithInfoSlovakAdapter
import com.example.plantme.data.Databse
import com.example.plantme.data.entities.CareActivity
import com.example.plantme.data.entities.Flower
import com.example.plantme.databinding.ItemFlowerInRecyclerViewHomeBinding
import com.example.plantme.fragments.HomeDirections
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FlowerInRecyclerViewAdapter (
    var flowersToShow: List<Flower>,
    ): RecyclerView.Adapter<FlowerInRecyclerViewAdapter.FlowerInRecyclerViewHolder>() {
    private val WATER = 1
        inner class FlowerInRecyclerViewHolder(val binding: ItemFlowerInRecyclerViewHomeBinding): RecyclerView.ViewHolder(binding.root) {

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlowerInRecyclerViewHolder {
            val binding =  ItemFlowerInRecyclerViewHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return FlowerInRecyclerViewHolder(binding)
        }


        override fun onBindViewHolder(holder: FlowerInRecyclerViewHolder, position: Int) {
            val dao = holder.itemView.context?.let { Databse.getInstance(it).createDao() }
            with(holder)
            {
                binding.itemInRecyclerView.setOnClickListener {
                    val parameter: String = flowersToShow[position].name
                    val action = HomeDirections.actionFirstFragmentToSecondFragment(parameter)
                    it.findNavController().navigate(action)
                }
                binding.tvName.text= flowersToShow[position].name
                binding.tvType.text= flowersToShow[position].type
                if (flowersToShow[position].picture != null) {
                    binding.pictureFlower.setImageBitmap(flowersToShow[position].picture?.let { BitmapFactory.decodeByteArray(flowersToShow[position].picture, 0, it.size) })
                } else {
                    binding.pictureFlower.setImageResource(R.drawable.ic_baseline_local_florist_24)
                }
                binding.pictogramWater.setOnClickListener {
                    val act = CareActivity(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), flowersToShow[position].name, 1)
                    runBlocking { dao?.insertNewActivity(act)}
                    notifyDataSetChanged()
                }
                dao?.let { TextInTextViewWithInfoSlovakAdapter().getTextForInfo(it,binding.tvInfoAboutWatering, flowersToShow[position].name,WATER ) }
            }

        }
        override fun getItemCount(): Int {
            return flowersToShow.size
        }
}