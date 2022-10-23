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

fun getImage(name : String): String {
    var image = StringBuilder()
    for (counter in name) {
        if(counter != '\''){
            if(counter == ' ') {
                image.append("_")
            }else{
                if(counter.isUpperCase()){
                    image.append(counter.lowercaseChar())
                } else {
                    image.append(counter)
                }
            }
        }
    }
    return image.toString()
}