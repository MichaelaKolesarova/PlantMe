package com.example.plantme

import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.plantme.data.Databse
import com.example.plantme.data.MyDao
import com.example.plantme.data.entities.CareActivity
import com.example.plantme.data.entities.Flower
import com.example.plantme.databinding.FragmentHomeBinding
import com.example.plantme.databinding.ItemFlowerInRecyclerViewHomeBinding
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.coroutines.coroutineContext

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
                dao?.let { getTextForInfo(it,binding.tvInfoAboutWatering, flowersToShow[position].name ) }



            }

        }


        override fun getItemCount(): Int {
            return flowersToShow.size
        }
    private fun getdaysToDoActivityAgain(dao: MyDao, name: String): Int
    {

        return runBlocking {
            val acts = dao.getSpecificActivities(name, 1)
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

    private fun getTextForInfo(dao: MyDao, textView: TextView, name:String)
    {

        runBlocking {
            var frequency = dao.getSpecificFlower(name).watering_frequency

            var helpTextBefore = " do ďalšieho "
            var helpTextAfter = " pozadu "
            helpTextBefore = helpTextBefore + "zalievania"


            var daysToActivity = frequency + getdaysToDoActivityAgain(dao, name)
            if (daysToActivity >= 0) {
                if (daysToActivity == 0){
                    textView.text = "dnes je nutné kvet zaliať"
                }
                else if (daysToActivity == 1){
                    textView.text = daysToActivity.toString()+ " deň" + helpTextBefore
                } else if (daysToActivity >= 2 && daysToActivity<= 4) {
                    textView.text = daysToActivity.toString() + " dni"+ helpTextBefore
                } else  {
                    textView.text = daysToActivity.toString() + " dní" + helpTextBefore
                }
            } else {
                daysToActivity++
                if (daysToActivity == -1){
                    textView.text = (-1*daysToActivity).toString() + " deň" + helpTextAfter
                }else if (daysToActivity <= -2 && daysToActivity >= -4) {
                    textView.text = (-1*daysToActivity).toString() + " dni" + helpTextAfter
                }else if (daysToActivity == 0) {
                    textView.text = "dnes je nutné kvet zaliať"
                }
                else
                {
                    textView.text = (-1*daysToActivity).toString() + helpTextAfter
                }
                textView.setTextColor(Color.RED)
            }

        }
    }






}