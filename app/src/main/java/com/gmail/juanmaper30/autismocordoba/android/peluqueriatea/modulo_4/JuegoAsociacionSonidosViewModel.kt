package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.modulo_4

import androidx.lifecycle.ViewModel
import android.content.Context
import android.util.Log
import com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.ObjetoSonidoPeluqueria
import com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.R

private const val TAG = "JuegoSonidosViewModel"

class JuegoAsociacionSonidosViewModel() : ViewModel() {

    var listaObjetos = mutableListOf<ObjetoSonidoPeluqueria>()
    var indice = 0
    var altavozPulsado = 0

    fun inicializarReproductor(context: Context) {
       if (listaObjetos.size == 0) {
           listaObjetos.add(
               ObjetoSonidoPeluqueria(
                   context,
                   "Secador",
                   R.raw.audio_secador
               )
           )
           listaObjetos.add(
               ObjetoSonidoPeluqueria(
                   context,
                   "Maquinilla",
                   R.raw.audio_maquinilla
               )
           )
           listaObjetos.add(
               ObjetoSonidoPeluqueria(
                   context,
                   "Spray",
                   R.raw.audio_spray
               )
           )
           listaObjetos.add(
               ObjetoSonidoPeluqueria(
                   context,
                   "Tijeras",
                   R.raw.audio_tijeras
               )
           )

           listaObjetos.shuffle()
           Log.d(
               TAG, "Orden de la lista: ${listaObjetos[0].nombreObjeto}, " +
                   "${listaObjetos[1].nombreObjeto}, ${listaObjetos[2].nombreObjeto} y " +
                   listaObjetos[3].nombreObjeto)
       }
    }


    fun reproducirSonidoPasoActual() {
        listaObjetos[indice].reproducirSonido()
    }

    fun incrementarNumeroPulsacionesAltavoz() {
        altavozPulsado++
        Log.d(TAG, "Altavoz pulsado: $altavozPulsado veces.")
    }

    fun reiniciarNumeroPulsacionesAltavoz() {
        altavozPulsado = 0
        Log.d(TAG, "Reinicio pulsaciones pulsado: $altavozPulsado.")
    }

    fun incrementarIndice() {
        indice++
        Log.d(TAG, "Indice incrementado a: $indice.")
    }

    fun hayAudioReproduciendose(): Boolean {
        return listaObjetos[indice].reproductor?.isPlaying ?: false
    }

    fun detenerAudio() {
        if (hayAudioReproduciendose()) {
            Log.d(TAG, "Parado audio")
            listaObjetos[indice].reproductor?.pause()
            listaObjetos[indice].reproductor?.seekTo(0)
        }
    }

    val nombreObjetoActual: String
        get() = listaObjetos[indice].nombreObjeto

    val objetoActual: ObjetoSonidoPeluqueria
        get() = listaObjetos[indice]


    override fun onCleared() {
        super.onCleared()

        for (numIndice in 0..3) {
            listaObjetos[numIndice].reproductor?.release()
            listaObjetos[numIndice].reproductor = null

            Log.d(TAG, "Liberado mediaplayer del indice: $numIndice")
        }
    }
}