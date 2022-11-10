package com.baub.firstorchard.game

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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

    var blueTreeImgs = arrayOf(R.drawable.blue_0,R.drawable.blue_1,R.drawable.blue_2,R.drawable.blue_3, R.drawable.blue_4)
    var greenTreeImgs = arrayOf(R.drawable.green_0,R.drawable.green_1,R.drawable.green_2,R.drawable.green_3, R.drawable.green_4)
    var yellowTreeImgs = arrayOf(R.drawable.yellow_0,R.drawable.yellow_1,R.drawable.yellow_2,R.drawable.yellow_3, R.drawable.yellow_4)
    var redTreeImgs = arrayOf(R.drawable.red_0,R.drawable.red_1,R.drawable.red_2,R.drawable.red_3, R.drawable.red_4)

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
            if( (diceText.equals("Red") || diceText.equals("Basket")) ) {
                if(redTotal > 0) {
                    redTotal--
                    binding.redTreeButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), redTreeImgs[redTotal]))
                }
                updateTotals()
            }
        }

        binding.greenTreeButton.setOnClickListener {
            if( (diceText.equals("Green") || diceText.equals("Basket")) ) {
                if(greenTotal > 0) {
                    greenTotal--
                    binding.greenTreeButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), greenTreeImgs[greenTotal]))
                }
                updateTotals()
            }
        }

        val onClickListener = binding.blueTreeButton.setOnClickListener {
            if ((diceText.equals("Blue") || diceText.equals("Basket"))) {
                if (blueTotal > 0) {
                    blueTotal--
                    binding.blueTreeButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), blueTreeImgs[blueTotal]))
                }
                updateTotals()
            }
        }

        binding.yellowTreeButton.setOnClickListener {
            if( (diceText.equals("Yellow") || diceText.equals("Basket")) ) {
                if(yellowTotal > 0) {
                    yellowTotal--
                    binding.yellowTreeButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), yellowTreeImgs[yellowTotal]))
                }
                updateTotals()
            }
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