package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    /* Variables modulo 2: Vamos a la peluqueria */
    var indiceInternoSecuenciaPasos = 0
    var listaIndicesPasosParaMostrar = mutableListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9)

    /* Variables usada en el modulo 2 y 3 para mostrar chico o chica*/
    var opcionChicoElegida: Boolean = true

    /* Variables modulo 3: Elijo mi peinado */
    var opcionPeinadoEscogida: Int = 2
    var opcionColorPeinadoEscogida: Int = 2
    var listaAvataresGuardados = mutableListOf<Int>(0, 0, 0)

    val getIndicePasoActualVamosPeluqueria: Int
        get() = listaIndicesPasosParaMostrar[indiceInternoSecuenciaPasos]

    fun incrementarIndice() {
        indiceInternoSecuenciaPasos++
    }

    fun decrementarIndice() {
        indiceInternoSecuenciaPasos--
    }

    fun reiniciarIndice() {
        indiceInternoSecuenciaPasos = 0
    }

    val estoyEnUltimoConsejo: Boolean
        get() = indiceInternoSecuenciaPasos + 1 == listaIndicesPasosParaMostrar.size

    val getOpcionChicoElegida: Boolean
        get() = opcionChicoElegida == true

    /* Modulo 3: Elijo mi peinado */


}