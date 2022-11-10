package com.baub.firstorchard.game

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.firstorchard.R
import com.example.firstorchard.R.id.*
import com.example.firstorchard.databinding.FragmentFirstGameBinding

class GameFirstFragment : Fragment() {

    private var _binding: FragmentFirstGameBinding? = null
    var redTotal:    Int = 4
    var greenTotal:  Int = 4
    var blueTotal:   Int = 4
    var yellowTotal: Int = 4
    var ravenTotal:  Int = 5
    var diceText:    String = ""
    var diceValues = arrayOf("Red","Green","Blue","Yellow","Basket","Raven")
    //var diceValues = arrayOf("Raven","Raven","Raven","Raven","Raven","Raven")
    //var diceValues = arrayOf("Basket","Basket","Basket","Basket","Basket","Basket")
    var diceRoll:    Int = 0
    val collectedFruit: MutableList<ImageView> = arrayListOf()


    var tileLocations= arrayOfNulls<ImageView>(5)
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
        tileLocations[0] = binding.tileImage1
        tileLocations[1] = binding.tileImage2
        tileLocations[2] = binding.tileImage3
        tileLocations[3] = binding.tileImage4
        tileLocations[4] = binding.tileImage5


        binding.redTreeButton.setOnClickListener {
            if( (diceText.equals("Red") || diceText.equals("Basket")) ) {
                if(redTotal > 0) {
                    redTotal--
                    binding.redTreeButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), redTreeImgs[redTotal]))
                    addToBasket(R.drawable.red_apple)
                }
                clearDice()
            }
        }

        binding.greenTreeButton.setOnClickListener {
            if( (diceText.equals("Green") || diceText.equals("Basket")) ) {
                if(greenTotal > 0) {
                    greenTotal--
                    binding.greenTreeButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), greenTreeImgs[greenTotal]))
                    addToBasket(R.drawable.green_apple)
                }
                clearDice()
            }
        }

        binding.blueTreeButton.setOnClickListener {
            if ((diceText.equals("Blue") || diceText.equals("Basket"))) {
                if (blueTotal > 0) {
                    blueTotal--
                    binding.blueTreeButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), blueTreeImgs[blueTotal]))
                    addToBasket(R.drawable.blueplum)
                }
                clearDice()
            }
        }

        binding.yellowTreeButton.setOnClickListener {
            if( (diceText.equals("Yellow") || diceText.equals("Basket")) ) {
                if(yellowTotal > 0) {
                    yellowTotal--
                    binding.yellowTreeButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), yellowTreeImgs[yellowTotal]))
                    addToBasket(R.drawable.yellow_pear)
                }
                clearDice()
            }
        }

        binding.diceButton.setOnClickListener {
            if(diceText == ""){
                diceRoll = (0 .. 5).random()
                binding.diceValueText.setText(diceValues[diceRoll])
                diceText = binding.diceValueText.text.toString() //reset this to be used in other comparisons
                if(diceText == "Raven"){
                    moveRaven() //moves the raven and then decrements the count, to ensure it doesn't move onto a tile until the first move
                    if(ravenTotal > 0) {
                        ravenTotal--
                        //binding.ravenValueText.setText(ravenTotal.toString())
                        updateTotals()
                    } else {
                        //game over
                        resetBoard()
                        findNavController().navigate(action_GameFirstFragment_to_GameSecondFragment)
                    }//end game over
                } //end raven roll
            }//end check for outstanding turn
        }//end dice button click
    }


    fun addToBasket(imgId: Int){
        val imageView = ImageView(context)
        imageView.setImageResource(imgId)
        //"throw" the fruit in the basket by specifying a random place within the basket's bounds, minus the dimensions of the fruit
        imageView.x = (0 ..(  (binding.basketLayout.width).toInt() - 164 ) ).random().toFloat()
        imageView.y = (0 .. ( (binding.basketLayout.height).toInt()) - 184 ).random().toFloat()
//        System.out.println("Image x: " + imageView.x)
//        System.out.println("Image y: " + imageView.y)
//        System.out.println("layout count before: " + binding.basketLayout.childCount)
        binding.basketLayout.addView(imageView)
        //System.out.println("layout count before: " + binding.basketLayout.childCount)
        updateTotals()
    }

    fun clearDice(){
        binding.diceValueText.setText("")
        diceText = ""
    }

    fun updateTotals(){
        binding.basketValueText.setText("R:"+(4-redTotal)+" G:"+(4-greenTotal)+" B:"+(4-blueTotal)+" Y:"+(4-yellowTotal))

        if(redTotal==0 && greenTotal ==0 && blueTotal == 0 && yellowTotal == 0){
            resetBoard()
            playSound(R.raw.alldoney)
            Thread.sleep(2500)
            playSound(R.raw.woah)
            findNavController().navigate(action_GameFirstFragment_to_GameThirdFragment)
        }
    }

    fun moveRaven() {
        playSound(R.raw.raven)
        if(ravenTotal > 0) {
            binding.ravenPieceImage.x = tileLocations[5 - ravenTotal]!!.x
            binding.ravenPieceImage.y = tileLocations[5 - ravenTotal]!!.y
        }
        if(ravenTotal==1){
            Thread.sleep(1000)
            playSound(R.raw.scared)
        } else if (ravenTotal == 0){
            Thread.sleep(1000)
            playSound(R.raw.crying)
        }
    }

    fun playSound(resid: Int){
        var resId = getResources().getIdentifier(resid.toString(),
            "raw", activity?.packageName)

        val mediaPlayer = MediaPlayer.create(activity, resId)
        mediaPlayer.start()
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