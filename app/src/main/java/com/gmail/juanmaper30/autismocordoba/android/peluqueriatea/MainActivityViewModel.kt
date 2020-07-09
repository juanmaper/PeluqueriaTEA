package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "MainActivityViewModel"

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
    fun guardarAvatarEnLista(nuevoAvatar: Avatar) {
        Log.d(TAG, "Codigo asociado al avatar a guardar: ${nuevoAvatar.getCodigo()}.")
        Log.d(TAG, "Tamaño lista previo: ${numeroAvataresGuardadosEnLista()}")

        listaAvataresGuardados[2] = listaAvataresGuardados[1]
        listaAvataresGuardados[1] = listaAvataresGuardados[0]
        listaAvataresGuardados[0] = nuevoAvatar.getCodigo()

        Log.d(TAG, "Tamaño lista posterior: ${numeroAvataresGuardadosEnLista()}")

        Log.d(TAG, "Lista resultado-> [0]: ${listaAvataresGuardados[0]}, [1]: " +
                "${listaAvataresGuardados[1]}, [2]: ${listaAvataresGuardados[2]}")
    }

    fun numeroAvataresGuardadosEnLista(): Int {
        if (listaAvataresGuardados[2] != 0) {
            return 3
        }
        if (listaAvataresGuardados[1] != 0) {
            return 2
        }
        if (listaAvataresGuardados[0] != 0) {
            return 1
        }
        return 0
    }

    fun convertirListaAvataresACadena(): String {
        return listaAvataresGuardados.joinToString(separator = ",")
    }

    fun cargarListaAvataresConCadena(cadena: String) {
        val listaRecuperada = cadena.split(",").map{ it.toInt() }
        Log.d(TAG, "Recuperada cadena de avatares ->[0]: ${listaRecuperada[0]}- [1]: " +
                "${listaRecuperada[1]}- [2]: ${listaRecuperada[2]}")

        listaAvataresGuardados[0] = listaRecuperada[0]
        listaAvataresGuardados[1] = listaRecuperada[1]
        listaAvataresGuardados[2] = listaRecuperada[2]
    }

    fun reiniciarListaAvatares() {
        listaAvataresGuardados[0] = 0
        listaAvataresGuardados[1] = 0
        listaAvataresGuardados[2] = 0
    }

}