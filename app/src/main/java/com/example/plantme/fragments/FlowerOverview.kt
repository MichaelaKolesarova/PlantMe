package com.example.plantme.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plantme.adapters.ActivityInRecyclerViewAdapter
import com.example.plantme.R
import com.example.plantme.data.Databse
import com.example.plantme.databinding.FragmentFlowerOverviewBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


/**
 * A simple odf Fragment subclass as the second destination in the navigation.
 * Represents the basic info about the flower (name, type, picture)
 * Helds information about the last time when the flower was taken care of and notifies when it should be again
 * intialises the RecyclerViewAdapter to handle 4 possibilities to take care of
 */
class FlowerOverview() : Fragment() {
    private val WATER = 1
    private val FERTILIZE = 2
    private val REPOT = 3
    private val CLEAN = 4

    private var _binding: FragmentFlowerOverviewBinding? = null

    private val binding get() = _binding!!

    private val listOfActivities = listOf(WATER,FERTILIZE,REPOT,CLEAN)

    /**
     * initializes name, type and picture
     * if the picture is presnet, uses the saved picturem if not, uses default pictogram
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val dao = context?.let { Databse.getInstance(it).createDao() }
        val argumentValue = arguments?.getString("name")
        _binding = FragmentFlowerOverviewBinding.inflate(inflater, container, false)

        return runBlocking {
            val flower = argumentValue?.let { dao?.getSpecificFlower(it) }!!


            binding.tvName.text = flower.name
            binding.tvType.text = flower.type
            if (flower.picture != null) {
                binding.pictureFlower.setImageBitmap(flower.picture?.let {
                    BitmapFactory.decodeByteArray(
                        flower.picture,
                        0,
                        it.size
                    )
                })
            } else {
                binding.pictureFlower.setImageResource(R.drawable.ic_baseline_local_florist_24)
            }
            binding.rvActivities.adapter =
                listOfActivities?.let { ActivityInRecyclerViewAdapter(it, flower.name) }
            binding.rvActivities.layoutManager = LinearLayoutManager(context)

            return@runBlocking binding.root
        }



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}