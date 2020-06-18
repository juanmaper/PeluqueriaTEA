package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    var indiceInternoSecuenciaPasos = 0
    var listaIndicesPasosParaMostrar = mutableListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9)
    var opcionChicoElegida: Boolean = true

    val indicePasoActual: Int
        get() = listaIndicesPasosParaMostrar[indiceInternoSecuenciaPasos]

    fun incrementarIndice() {
        indiceInternoSecuenciaPasos++
    }

    fun decrementarIndice() {
        indiceInternoSecuenciaPasos--
    }

    fun reiniciar() {
        indiceInternoSecuenciaPasos = 0
    }

    val estoyEnUltimoConsejo: Boolean
        get() = indiceInternoSecuenciaPasos + 1 == listaIndicesPasosParaMostrar.size

    val opcionMostrarChicoElegida: Boolean
        get() = opcionChicoElegida == true

}