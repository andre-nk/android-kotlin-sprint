package com.plcoding.jetpackcomposepokedex.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import com.plcoding.jetpackcomposepokedex.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import com.google.accompanist.coil.CoilImage
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Pokemon
import com.plcoding.jetpackcomposepokedex.data.remote.responses.Type
import com.plcoding.jetpackcomposepokedex.util.Resource
import com.plcoding.jetpackcomposepokedex.util.parseTypeToColor
import kotlin.math.round

@Composable
fun PokemonDetailScreen(
    dominantColor: Color,
    pokemonName: String,
    navController: NavController,
    customTopPadding: Dp = 20.dp,
    customSpriteSize: Dp = 200.dp,
    viewModel: PokemonDetailViewModel = hiltNavGraphViewModel()
) {
    val pokemonInfo = produceState<Resource<Pokemon>>(initialValue = Resource.Loading()) {
        value = viewModel.getPokemonInfo(pokemonName)
    }.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(dominantColor)
            .padding(bottom = 16.dp)
    ) {
        PokemonDetailNavigationBar(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .align(Alignment.TopCenter)
        )
        PokemonDetailWrapper(
            pokemonInfo = pokemonInfo,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = customTopPadding + customSpriteSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .shadow(10.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.surface)
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            loadingModifier = Modifier
                .size(64.dp)
                .align(Alignment.Center)
                .padding(
                    top = customTopPadding + customSpriteSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            if (pokemonInfo is Resource.Success<*>) {
                pokemonInfo.data?.sprites?.let {
                    CoilImage(
                        data = it.front_default,
                        contentDescription = pokemonInfo.data.name + "'s sprite",
                        fadeIn = true,
                        modifier = Modifier
                            .size(customSpriteSize)
                            .offset(y = customTopPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PokemonDetailNavigationBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(
            Brush.verticalGradient(
                listOf(
                    Color.Black,
                    Color.Transparent
                )
            )
        ),
        contentAlignment = Alignment.TopStart
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "back_arrow",
            tint = Color.White,
            modifier = Modifier
                .size(24.dp)
                .offset(16.dp, 16.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
    }
}

@Composable
fun PokemonDetailWrapper(
    pokemonInfo: Resource<Pokemon>,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier
) {
    when (pokemonInfo) {
        is Resource.Success -> {
            PokemonDetailSection(
                pokemonInfo = pokemonInfo.data!!,
                modifier = modifier.offset(y = (-20).dp),
            )
        }
        is Resource.Error -> {
            Text(
                text = pokemonInfo.message ?: "Something went wrong.",
                color = Color.Red,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier

            )
        }
        is Resource.Loading -> {
            CircularProgressIndicator(
                color = Color.White,
                modifier = loadingModifier
            )
        }
    }
}

@Composable
fun PokemonDetailSection(
    pokemonInfo: Pokemon,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .offset(y = 100.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "No. ${pokemonInfo.id}: ${pokemonInfo.name.capitalize()}",
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onSurface
        )
        PokemonTypeBadge(types = pokemonInfo.types)
        PokemonMeasurementSection(
            pokemonHeight = pokemonInfo.height,
            pokemonWeight = pokemonInfo.weight
        )
    }
}

@Composable
fun PokemonTypeBadge(
    types: List<Type>
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        for (type in types) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .clip(CircleShape)
                    .background(parseTypeToColor(type))
                    .height(36.dp)
            ) {
                Text(
                    text = type.type.name.capitalize(),
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun PokemonMeasurementSection(
    pokemonWeight: Int,
    pokemonHeight: Int,
    customSectionHeight: Dp = 64.dp
) {
    val roundedPokemonWeight = remember {
        round(pokemonWeight * 100f) / 1000f
    }

    val roundedPokemonHeight = remember {
        round(pokemonHeight * 100f) / 1000f
    }

    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PokemonMeasurementItem(
            dataIcon = painterResource(id = R.drawable.ic_weight),
            dataUnit = "kg",
            dataValue = roundedPokemonWeight,
            modifier = Modifier.weight(1f)
        )
        Spacer(
            modifier = Modifier
                .size(1.dp, customSectionHeight)
                .background(Color.LightGray)
        )
        PokemonMeasurementItem(
            dataIcon = painterResource(id = R.drawable.ic_height),
            dataUnit = "m",
            dataValue = roundedPokemonHeight,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun PokemonMeasurementItem(
    dataIcon: Painter,
    dataUnit: String,
    dataValue: Float,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(
            painter = dataIcon,
            contentDescription = "${dataValue}'s Icon",
            tint = MaterialTheme.colors.onSurface,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "$dataValue $dataUnit",
            color = MaterialTheme.colors.onSurface
        )
    }
}