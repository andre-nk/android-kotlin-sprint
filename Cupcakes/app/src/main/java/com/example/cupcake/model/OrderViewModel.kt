package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

private const val PRICE_PER_CUPCAKE = 2.00
private const val PRICE_FOR_SAME_DAY_DELIVERY = 3.00

class OrderViewModel: ViewModel() {
    private val _quantity =  MutableLiveData(0)
    val quantity: LiveData<Int> = _quantity

    private val _flavor = MutableLiveData("")
    val flavor: LiveData<String> = _flavor

    private val _customerName = MutableLiveData("")
    val customerName: LiveData<String> = _customerName

    private val _customerAddress = MutableLiveData("")
    val customerAddress: LiveData<String> = _customerAddress

    private val _pickup = MutableLiveData("")
    val pickup: LiveData<String> = _pickup

    private val _price = MutableLiveData(0.0)
    val price: LiveData<Double> = _price

    val datePickupOptions = getPickupOptions()

    private fun getPickupOptions(): List<String>{
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()

        repeat(4){
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }

        return options
    }

    init {
        resetOrder()
    }

    fun resetOrder(){
        _quantity.value = 0
        _flavor.value = ""
        _customerName.value = ""
        _customerAddress.value = ""
        _pickup.value = datePickupOptions[0]
        _price.value = 0.0
    }

    fun setQuantity(totalCupcakes: Int){
        _quantity.value = totalCupcakes
        calculatePrice()
    }

    fun setFlavor(flavor: String){
        _flavor.value = flavor
    }

    fun setCustomerInfo(name: String, address: String){
        _customerName.value = name
        _customerAddress.value = address
    }

    fun setPickup(pickupDate: String){
        _pickup.value = pickupDate
        calculatePrice()
    }

    private fun calculatePrice(){
        var tempPrice: Double = (quantity.value ?: 0) * PRICE_PER_CUPCAKE

        if(pickup.value == datePickupOptions[0]){
            tempPrice += PRICE_FOR_SAME_DAY_DELIVERY
        }

        _price.value = tempPrice
    }
}