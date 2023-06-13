package com.example.plantme.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plantme.adapters.FlowerInRecyclerViewAdapter
import com.example.plantme.R
import com.example.plantme.data.Databse

import com.example.plantme.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class Home : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val dao = context?.let { Databse.getInstance(it).createDao() }
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.rvFlowers.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        binding.add.setOnClickListener {
            findNavController().navigate(R.id.action_Home_to_addNewFlower)
        }
        lifecycleScope.launch {
            var flowers = dao?.getAllFlowers()
            binding.rvFlowers.adapter = flowers?.let { FlowerInRecyclerViewAdapter(it) }
            binding.rvFlowers.layoutManager = LinearLayoutManager(this@Home.context)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}