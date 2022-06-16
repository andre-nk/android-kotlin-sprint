package com.andrenk.tabletop

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun diceRoll(){
        val dice = Dice(6)
        val result = dice.roll()
        assertTrue("The roll result wasn't between 1 and 6", result in 1..6)
    }
}