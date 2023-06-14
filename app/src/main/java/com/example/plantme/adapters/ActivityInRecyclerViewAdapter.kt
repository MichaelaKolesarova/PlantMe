package com.example.plantme.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plantme.R
import com.example.plantme.TextInTextViewWithInfoSlovakAdapter
import com.example.plantme.data.Databse
import com.example.plantme.data.MyDao
import com.example.plantme.data.entities.CareActivity
import com.example.plantme.databinding.ItemActivityInRecyclerViewOverviewBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.runBlocking


/**
 * Adapter that manages behaviour of the recyclerView in Fragment [FloverOverview]
 * sets type of activity and the need of the flower to be taken care of
 * sets onClickListener on the pictogram to take care of the flower
 */
class ActivityInRecyclerViewAdapter(var activities: List<Int>, name: String)
    : RecyclerView.Adapter<ActivityInRecyclerViewAdapter.ActivityInRecyclerViewHolder>() {
        val name = name
    private val WATER = 1
    private val FERTILIZE = 2
    private val REPOT = 3
    private val CLEAN = 4

    /**
     * initialisation of the holder
     */
    inner class ActivityInRecyclerViewHolder(val binding: ItemActivityInRecyclerViewOverviewBinding): RecyclerView.ViewHolder(binding.root) {}
        /**
         * pairing recyclerView with hte binding
         */
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityInRecyclerViewHolder {
            val binding =  ItemActivityInRecyclerViewOverviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ActivityInRecyclerViewHolder(binding)
        }
        /**
         * setting text for info using pre-made functions that creates slovak sentence from [TextInTextViewWithInfoSlovakAdapter]
         * setting onClickListener on Pictogram to take care of the flower
         */
        override fun onBindViewHolder(holder: ActivityInRecyclerViewHolder, position: Int) {
            val context = holder.itemView.context
            val dao = holder.itemView.context?.let { Databse.getInstance(it).createDao() }

            with(holder)
            {
                when (activities[position]) {
                    WATER -> {binding.tvActivity.text = "Polievanie"
                        binding.pictogram.setImageResource(R.drawable.water)
                        inetmInRecyclerViewSetup(dao, binding.tvInfoAboutActivity, binding.pictogram, WATER, context) }
                    FERTILIZE -> {binding.tvActivity.text = "Hnojenie"
                        binding.pictogram.setImageResource(R.drawable.fertilise)
                        inetmInRecyclerViewSetup(dao, binding.tvInfoAboutActivity, binding.pictogram, FERTILIZE, context) }
                    REPOT -> {binding.tvActivity.text = "Presádzanie"
                        binding.pictogram.setImageResource(R.drawable.repot)
                        inetmInRecyclerViewSetup(dao, binding.tvInfoAboutActivity, binding.pictogram, REPOT, context) }
                    CLEAN -> {binding.tvActivity.text = "Čistenie"
                        binding.pictogram.setImageResource(R.drawable.clean)
                        inetmInRecyclerViewSetup(dao, binding.tvInfoAboutActivity, binding.pictogram, CLEAN, context) }
                }
            }

        }
    /**
     * setting text and on ClickListener
     */
    private fun inetmInRecyclerViewSetup(dao: MyDao?, textView: TextView, imageView: ImageButton, type:Int, context: Context) {
        dao?.let { TextInTextViewWithInfoSlovakAdapter().getTextForInfo(it, textView, name, type, context) }
        imageView.setOnClickListener {
            val act = CareActivity(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), name, type)
            runBlocking { dao?.insertNewActivity(act) }}
        textView.invalidate()
    }

    override fun getItemCount(): Int {
            return activities.size
        }











}
