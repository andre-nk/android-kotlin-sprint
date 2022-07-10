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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {
    var pokedexList = mutableStateOf<List<PokedexListEntry>>(listOf())
    var loadingError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var isEndReached = mutableStateOf(false)
    var isSearching = mutableStateOf(false)

    private var currentPage = 0
    private var cachedPokemonList = listOf<PokedexListEntry>()
    private var isSearchStarting = true

    init {
        paginatePokemon(false)
    }

    fun searchPokemon(query: String) {
        val targetList = if (isSearchStarting) {
            pokedexList.value
        } else {
            cachedPokemonList
        }

        //Default thread for searching (heavyweight)
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                //Cancel / stop search process, then reset the used PokedexList to cached (initial)
                pokedexList.value = cachedPokemonList
                isSearching.value = false
                isSearchStarting = true
                return@launch
            }

            //If query is not empty:
            val searchResult = targetList.filter {
                it.pokemonName.contains(query.trim(), ignoreCase = true) ||
                        it.number.toString().contains(query.trim(), ignoreCase = true)
            }

            if(isSearchStarting){
                //Caching the initial list (for faster reload after search)
                cachedPokemonList = pokedexList.value
                isSearchStarting = false
            }

            //Replacing the used pokedexList to the search result
            pokedexList.value = searchResult
            isSearching.value = true
        }
    }

    fun paginatePokemon(resetList: Boolean) {
        Log.d("PAGINATE LAUNCHED", "TEST")
        if(resetList){
            currentPage = 0
        }

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