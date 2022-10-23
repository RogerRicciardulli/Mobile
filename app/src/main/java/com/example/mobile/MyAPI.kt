package com.example.mobile

import retrofit2.Call
import retrofit2.http.GET

interface MyAPI {

    @GET("/films")
    fun getFilms() : Call<List<MovieResponse>>

    @GET("/people")
    fun getCharacters() : Call<List<CharacterResponse>>
}