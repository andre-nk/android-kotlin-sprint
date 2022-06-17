package com.andrenk.awesomeness.data

import com.andrenk.awesomeness.R
import com.andrenk.awesomeness.model.AwesomeQuote

class DataSource {
    fun loadAwesomeness() : List<AwesomeQuote> {
        return listOf(
            AwesomeQuote(R.string.quote_1, R.drawable.image1),
            AwesomeQuote(R.string.quote_2, R.drawable.image2),
            AwesomeQuote(R.string.quote_3, R.drawable.image3),
            AwesomeQuote(R.string.quote_4, R.drawable.image4),
            AwesomeQuote(R.string.quote_5, R.drawable.image5),
            AwesomeQuote(R.string.quote_6, R.drawable.image6),
            AwesomeQuote(R.string.quote_7, R.drawable.image7)
        )
    }
}