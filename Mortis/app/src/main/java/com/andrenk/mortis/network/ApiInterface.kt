package com.andrenk.mortis.network

import retrofit2.http.GET

interface ApiInterface{
    @GET("people")
    suspend fun getPeople(): PeopleResponse
}