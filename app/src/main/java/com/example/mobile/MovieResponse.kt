package com.example.mobile

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieResponse(
    var id: String?,
    var title: String,
    var original_title: String,
    var original_title_romanised: String,
    var description: String,
    var director: String,
    var producer: String,
    var release_date: String,
    var running_time: String,
    var rt_score: String
)
