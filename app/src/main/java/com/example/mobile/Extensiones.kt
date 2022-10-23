package com.example.mobile

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.mobile.helper.SP
import com.example.mobile.helper.SharedPreferencesHelper
import com.example.mobile.list.movie.MovieDetail

fun Context.mensajeCorto(texto: String){
    Toast.makeText(this, texto, Toast.LENGTH_SHORT).show()
}

fun MutableList<Int>.intercambiarElementos(index1 : Int, index2 : Int){
    val tmp = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
}

fun Context.registrarPendiente(texto: String) = Log.i("TODO", texto)

fun convertToMovie(contador: Int): List<MovieDetail> {
    var sharedPreferencesHelper: SharedPreferencesHelper? = null
    val allFilms = sharedPreferencesHelper!!.getString(SP.MOVIES_WITHOUT_DETAIL)
    Log.d("LOG", "Data guardada: " + allFilms)
    val OriginalTitle = StringBuilder()
    val OriginalTitleRomanised = StringBuilder()
    val Description = StringBuilder()
    val DirectorName = StringBuilder()
    val ProductorName = StringBuilder()
    val ReleaseDate = StringBuilder()
    val RunningTime = StringBuilder()
    val RtScore = StringBuilder()

    val elements = arrayOf(
        "OriginalTitle",
        "OriginalTitleRomanised",
        "Description",
        "DirectorName",
        "ProductorName",
        "ReleaseDate",
        "RunningTime",
        "RtScore"
    )
    var cont = 0
    var actualElement: String
    var beginning = 0
    var beginningAddition = 2

    var films = ArrayList<MovieDetail>()
    var movie = MovieDetail()
    for (counter in allFilms.indices) {
        if (films.count() <= contador - 1) {
            if (allFilms[counter] == '|') {
                actualElement = elements[cont]
                when (actualElement) {
                    "OriginalTitle" -> {
                        if (films.count() == 1)
                            beginning += 2
                        OriginalTitle.append(allFilms.substring(beginning, counter - 1))
                        beginning = counter + 2
                        movie.originalTitle = OriginalTitle.toString()
                    }
                    "OriginalTitleRomanised" -> {
                        OriginalTitleRomanised.append(allFilms.substring(beginning, counter - 1))
                        beginning = counter + 2
                        movie.originalTitleRomanised = OriginalTitleRomanised.toString()
                    }
                    "Description" -> {
                        Description.append(allFilms.substring(beginning, counter - 1))
                        beginning = counter + 2
                        movie.description = Description.toString()
                    }
                    "DirectorName" -> {
                        DirectorName.append(allFilms.substring(beginning, counter - 1))
                        beginning = counter + 2
                        movie.directorName = DirectorName.toString()
                    }
                    "ProductorName" -> {
                        ProductorName.append(allFilms.substring(beginning, counter - 1))
                        beginning = counter + 2
                        movie.productorName = ProductorName.toString()
                    }
                    "ReleaseDate" -> {
                        ReleaseDate.append(allFilms.substring(beginning, counter - 1))
                        beginning = counter + 2
                        movie.releaseDate = ReleaseDate.toString()
                    }
                    "RunningTime" -> {
                        RunningTime.append(allFilms.substring(beginning, counter - 1))
                        beginning = counter + 2
                        movie.runningTime = RunningTime.toString()
                    }
                    "RtScore" -> {
                        RtScore.append(allFilms.substring(beginning, counter - 1))
                        beginning = counter + beginningAddition
                        movie.rtScore = RtScore.toString()
                    }
                }
                cont += 1
            }
            if (allFilms[counter] == '*') {
                Log.d(
                    "LOG",
                    movie.originalTitle + movie.originalTitleRomanised + movie.description + movie.directorName +
                            movie.productorName + movie.releaseDate + movie.runningTime + movie.rtScore
                )
                films.add(
                    MovieDetail(
                        movie.originalTitle,
                        movie.originalTitleRomanised,
                        movie.description,
                        movie.directorName,
                        movie.productorName,
                        movie.releaseDate,
                        movie.runningTime,
                        movie.rtScore
                    )
                )
                OriginalTitle.setLength(0)
                OriginalTitleRomanised.setLength(0)
                Description.setLength(0)
                DirectorName.setLength(0)
                ProductorName.setLength(0)
                ReleaseDate.setLength(0)
                RunningTime.setLength(0)
                RtScore.setLength(0)
                cont = 0
                beginningAddition = 4
            }
        }
    }
    return films;
}