package com.plcoding.jetpackcomposepokedex.ui.components

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.plcoding.jetpackcomposepokedex.data.remote.models.PokedexListEntry
import com.plcoding.jetpackcomposepokedex.repository.PokemonRepository
import com.plcoding.jetpackcomposepokedex.util.Constants.LIMIT_SIZE
import com.plcoding.jetpackcomposepokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private var currentPage = 0

    var pokedexList = mutableStateOf<List<PokedexListEntry>>(listOf())
    var loadingError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var isEndReached = mutableStateOf(false)

    init {
        paginatePokemon()
    }

    fun paginatePokemon() {
        Log.d("PAGINATE LAUNCHED", "TEST")

        viewModelScope.launch {
            when (val result = repository.getPokemonList(LIMIT_SIZE, currentPage * LIMIT_SIZE)) {
                is Resource.Success -> {
                    isEndReached.value = currentPage * LIMIT_SIZE >= result.data!!.count
                    val pokedexEntries = result.data.results.map { entry ->
                        val pokedexNumber = if (entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }
                        val spriteUrl =
                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokedexNumber}.png"
                        PokedexListEntry(entry.name.capitalize(), spriteUrl, pokedexNumber.toInt())
                    }

                    currentPage++
                    loadingError.value = ""
                    isLoading.value = false
                    pokedexList.value += pokedexEntries
                }
                is Resource.Error -> {
                    loadingError.value = result.message!!
                }
            }
        }
    }

    fun getDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bitmap = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
        Palette.from(bitmap).generate {
            it?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}