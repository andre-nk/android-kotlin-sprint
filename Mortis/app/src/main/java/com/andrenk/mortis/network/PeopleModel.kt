package com.andrenk.mortis.network

data class PeopleResponse (
    val results: List<PeopleModel>
)

data class PeopleModel(
    val name: String
)
