package com.example.plantme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plantme.data.entities.Flower
import com.example.plantme.databinding.FragmentHomeBinding
import com.example.plantme.databinding.ItemFlowerInRecyclerViewHomeBinding

class FlowerInRecyclerViewAdapter (
    var flowersToShow: List<Flower>,
    ): RecyclerView.Adapter<FlowerInRecyclerViewAdapter.FlowerInRecyclerViewHolder>() {

        inner class FlowerInRecyclerViewHolder(val binding: ItemFlowerInRecyclerViewHomeBinding): RecyclerView.ViewHolder(binding.root) {
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlowerInRecyclerViewHolder{

            //val view = LayoutInflater.from(parent.context).inflate(R.layout.item_flower_in_recycler_view_home, parent, false)

            val binding =  ItemFlowerInRecyclerViewHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            return FlowerInRecyclerViewHolder(binding)
        }

        override fun onBindViewHolder(holder: FlowerInRecyclerViewHolder, position: Int) {
            with(holder)
            {
                binding.tvName.text= flowersToShow[position].name
                binding.tvType.text= flowersToShow[position].type
                //binding.pictureFlower. = flowersToShow[position].picture.
            }

        }

        override fun getItemCount(): Int {
            return flowersToShow.size
        }
}