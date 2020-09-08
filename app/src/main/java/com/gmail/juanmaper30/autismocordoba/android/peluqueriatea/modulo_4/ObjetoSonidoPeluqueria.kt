package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.modulo_4

import android.content.Context
import android.media.MediaPlayer
import java.io.Serializable

class ObjetoSonidoPeluqueria(context: Context, nombre: String, idRecursoAudio: Int): Serializable{

    var reproductor: MediaPlayer? = null
    var nombreObjeto: String = ""

    init {
        reproductor = MediaPlayer.create(context, idRecursoAudio)
        nombreObjeto = nombre
    }

    fun reproducirSonido() {
        reproductor?.start()
    }
}