package com.example.plantme

import android.graphics.BitmapFactory
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plantme.data.Databse
import com.example.plantme.data.entities.Flower
import com.example.plantme.databinding.FragmentFlowerOverviewBinding
import kotlinx.coroutines.launch

import kotlinx.coroutines.runBlocking


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FlowerOverview() : Fragment() {

    private val WATER = 1
    private val FERTILIZE = 2
    private val REPOT = 3
    private val CLEAN = 4



    private var _binding: FragmentFlowerOverviewBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val listOfActivities = listOf(WATER,FERTILIZE,REPOT,CLEAN)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val dao = context?.let { Databse.getInstance(it).createDao() }
        val argumentValue = arguments?.getString("name")

        lifecycleScope.launch {
            val flower = argumentValue?.let { dao?.getSpecificFlower(it) }!!

            binding.tvName.text = flower.name
            binding.tvType.text = flower.type
            if (flower.picture != null) {
                binding.pictureFlower.setImageBitmap(flower.picture?.let { BitmapFactory.decodeByteArray(flower.picture, 0, it.size) })
            } else
            {
                binding.pictureFlower.setImageResource(R.drawable.ic_baseline_local_florist_24)
            }
            binding.rvActivities.adapter = listOfActivities?.let { ActivityInRecyclerViewAdapter(it, flower.name) }
            binding.rvActivities.layoutManager = LinearLayoutManager(context)
        }

        _binding = FragmentFlowerOverviewBinding.inflate(inflater, container, false)



        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }






}