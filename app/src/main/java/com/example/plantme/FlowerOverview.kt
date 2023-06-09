package com.example.plantme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plantme.databinding.FragmentFlowerOverviewBinding
import com.example.plantme.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FlowerOverview : Fragment() {

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

        _binding = FragmentFlowerOverviewBinding.inflate(inflater, container, false)

        binding.rvActivities.adapter = listOfActivities?.let { ActivityInRecyclerViewAdapter(it) }
        binding.rvActivities.layoutManager = LinearLayoutManager(context)




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