package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    var indiceInternoLista = 0
    var listaIndicesPasosParaMostrar = mutableListOf<Int>(1, 2, 3, 4)

    val indicePasoActual: Int
        get() = listaIndicesPasosParaMostrar[indiceInternoLista]

    fun incrementarIndice() {
        indiceInternoLista++
    }

    fun decrementarIndice() {
        indiceInternoLista--
    }

    fun reiniciar() {
        indiceInternoLista = 0
    }

    val estoyEnUltimoConsejo: Boolean
        get() = indiceInternoLista + 1 == listaIndicesPasosParaMostrar.size

}