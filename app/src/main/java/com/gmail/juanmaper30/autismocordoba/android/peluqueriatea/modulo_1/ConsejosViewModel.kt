package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.modulo_1

import androidx.lifecycle.ViewModel

private const val TAG = "ConsejosViewModel"

class ConsejosViewModel : ViewModel() {

    var indiceActual = 1
    var numeroConsejos = 7

    val indiceConsejoActual: Int
        get() = indiceActual

    fun incrementarIndice() {
        indiceActual++
    }

    fun decrementarIndice() {
        indiceActual--
    }

    val estoyEnPrimerConsejo: Boolean
        get() = indiceActual == 1
    val estoyEnUltimoConsejo: Boolean
        get() = indiceActual == numeroConsejos
}