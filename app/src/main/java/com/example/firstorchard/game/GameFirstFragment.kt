package com.example.firstorchard.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.firstorchard.R
import com.example.firstorchard.databinding.FragmentFirstGameBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameFirstFragment : Fragment() {

    private var _binding: FragmentFirstGameBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstGameBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gameButton.setOnClickListener {
            findNavController().navigate(R.id.action_GameFirstFragment_to_GameSecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}