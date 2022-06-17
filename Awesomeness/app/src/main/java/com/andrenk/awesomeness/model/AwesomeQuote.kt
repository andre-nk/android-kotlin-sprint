package com.andrenk.awesomeness.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class AwesomeQuote(@StringRes val stringResourceId: Int, @DrawableRes val imageResourceId: Int)