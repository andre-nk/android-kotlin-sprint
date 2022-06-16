package com.andrenk.tipsy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.andrenk.tipsy.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private fun calculateTip(): Double {
        val costOfService = binding.costOfService.text.toString().toDoubleOrNull() ?: return 0.0

        val tipPercentage = when(binding.tipOptions.checkedRadioButtonId){
            R.id.tip_option_15 -> 0.15
            R.id.tip_option_18 -> 0.18
            R.id.tip_option_20 -> 0.20
            else -> 0.15
        }

        val rawResult = costOfService * tipPercentage
        return if(binding.roundUpSwitch.isChecked){
            kotlin.math.ceil(rawResult)
        } else {
            rawResult
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateBtn.setOnClickListener{
            val tip = calculateTip()
            val formattedTip = NumberFormat.getCurrencyInstance().format(tip)

            if(tip == 0.0){
                binding.textResult.text = "Please input a correct cost of service!"
            } else {
                binding.textResult.text = "Result: $formattedTip"
            }
        }
    }
}