package com.baub.firstorchard.game

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.firstorchard.R
import com.example.firstorchard.R.id.action_GameFirstFragment_to_GameSecondFragment
import com.example.firstorchard.R.id.action_GameFirstFragment_to_GameThirdFragment
import com.example.firstorchard.databinding.FragmentFirstGameBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameFirstFragment : Fragment() {

    private var _binding: FragmentFirstGameBinding? = null
    var redTotal:    Int = 4
    var greenTotal:  Int = 4
    var blueTotal:   Int = 4
    var yellowTotal: Int = 4
    var ravenTotal:  Int = 5
    var diceText:    String = ""
    var diceValues = arrayOf("Red","Green","Blue","Yellow","Basket","Raven")
    var diceRoll:    Int = 0

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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.redTreeButton.setOnClickListener {
            if( (redTotal > 0) && (diceText.equals("Red") || diceText.equals("Basket")) ) {
                redTotal--
                binding.redTotalText.setText("Red Total: " + redTotal.toString())
            }
            updateTotals()
        }
        binding.greenTreeButton.setOnClickListener {
            if( (greenTotal > 0) && (diceText.equals("Green") || diceText.equals("Basket"))) {
                greenTotal--
                binding.greenTotalText.setText("Green Total: " + greenTotal.toString())
            }
            updateTotals()
        }
        binding.blueTreeButton.setOnClickListener {
            if( (blueTotal > 0) && (diceText.equals("Blue")  || diceText.equals("Basket"))) {
                blueTotal--
                binding.blueTotalText.setText("Blue Total: " + blueTotal.toString())
            }
            updateTotals()
        }
        binding.yellowTreeButton.setOnClickListener {
            if( (yellowTotal > 0) && (diceText.equals("Yellow") || diceText.equals("Basket"))) {
                yellowTotal--
                binding.yellowTotalText.setText("Yellow Total: " + yellowTotal.toString())
            }
            updateTotals()
        }

        binding.diceButton.setOnClickListener {
            if(diceText == ""){
                diceRoll = (0 .. 5).random()
                binding.diceValueText.setText(diceValues[diceRoll])
                diceText = binding.diceValueText.text.toString() //reset this to be used in other comparisons
                if(diceText == "Raven"){
                    System.out.println("HIT RAVEN")
                    if(ravenTotal > 0) {
                        ravenTotal--
                        binding.ravenValueText.setText(ravenTotal.toString())
                        updateTotals()
                        //play caw sound
                    } else {
                        //game over
                        resetBoard()
                        findNavController().navigate(action_GameFirstFragment_to_GameSecondFragment)
                    }//end game over
                } //end raven roll
            }//end check for outstanding turn
        }//end dice button click
    }
    fun updateTotals(){
        binding.basketValueText.setText("R:"+(4-redTotal)+" G:"+(4-greenTotal)+" B:"+(4-blueTotal)+" Y:"+(4-yellowTotal))
        binding.diceValueText.setText("")
        diceText = ""
        if(redTotal==0 && greenTotal ==0 && blueTotal == 0 && yellowTotal == 0){
            resetBoard()
            findNavController().navigate(action_GameFirstFragment_to_GameThirdFragment)
        }

    }

    fun resetBoard(){
        redTotal = 4
        greenTotal = 4
        blueTotal = 4
        yellowTotal = 4
        ravenTotal = 5
        diceText = ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}