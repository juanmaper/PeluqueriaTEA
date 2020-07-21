package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import androidx.lifecycle.ViewModel

class AjustesGestionCitasViewModel : ViewModel() {
    /*
    val citas = mutableListOf<CitaPeluqueria>()

    init {

        for (i in 0 until 100) {
            val cita = CitaPeluqueria()
            cita.comentario = "Comentario de prueba $i"
            citas += cita
        }

    }
    */
    private val citaPeluqueriaRepositorio = CitaPeluqueriaRepository.get()
    val listaCitasPeluqueriaLiveData = citaPeluqueriaRepositorio.getCitasPeluqueria()
}