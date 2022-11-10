package com.baub.firstorchard.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.firstorchard.R
import com.example.firstorchard.databinding.FragmentFirstMainBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFirstFragment : Fragment() {

private var _binding: FragmentFirstMainBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

      _binding = FragmentFirstMainBinding.inflate(inflater, container, false)
      return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.menuButton.setOnClickListener {
//            findNavController().navigate(R.id.action_MainFirstFragment_to_MainSecondFragment)
//        }
    }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}