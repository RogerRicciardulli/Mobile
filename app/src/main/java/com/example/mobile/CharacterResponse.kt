package com.example.mobile

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterResponse(
    var id : String?,
    var nombre: String,
    var genero: String,
    var edad: String,
    var colorDeOjos: String,
    var colorDePelo: String,
    var pelicula: String
)
