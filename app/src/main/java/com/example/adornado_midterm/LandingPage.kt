package com.example.adornado_midterm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.adornado_midterm.databinding.FragmentLandingPageBinding


class LandingPage : Fragment() {

    private lateinit var binding: FragmentLandingPageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLandingPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnPlay.setOnClickListener {
            it.findNavController().navigate(LandingPageDirections.actionLandingPageToQuestionnaire())
        }
    }

    companion object {
        //coming soon
    }
}