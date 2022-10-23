package com.example.mobile

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterResponse(
    var id: String,
    var name: String,
    var gender: String?,
    var age: String,
    var eye_color: String,
    var hair_color: String,
)
