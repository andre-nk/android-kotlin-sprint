package com.plcoding.jetpackcomposepokedex.di

import com.plcoding.jetpackcomposepokedex.data.remote.PokemonApi
import com.plcoding.jetpackcomposepokedex.repository.PokemonRepository
import com.plcoding.jetpackcomposepokedex.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    //Define a provider that will live as long as the app lives, and can be accessed anywhere within the app

    @Singleton
    @Provides
    //Repository Provider
    fun providePokemonRepository(
        api: PokemonApi
    ) = PokemonRepository(api)

    @Singleton
    @Provides
    //Retrofit Instance Provider
    fun providePokemonApi() : PokemonApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
                //Connect the defined interface
            .create(PokemonApi::class.java)
    }
}
