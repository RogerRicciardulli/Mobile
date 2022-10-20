package com.example.mobile

import android.content.Context
import android.util.Log
import android.widget.Toast

fun Context.mensajeCorto(texto: String){
    Toast.makeText(this, texto, Toast.LENGTH_SHORT).show()
}

fun MutableList<Int>.intercambiarElementos(index1 : Int, index2 : Int){
    val tmp = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
}

fun Context.registrarPendiente(texto: String) = Log.i("TODO", texto)
