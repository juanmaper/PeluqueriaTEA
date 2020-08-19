package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import androidx.lifecycle.ViewModel
import java.util.*

class AjustesGestionCitasViewModel : ViewModel() {

    private val citaPeluqueriaRepositorio = CitaPeluqueriaRepository.get()
    val listaCitasPeluqueriaLiveData = citaPeluqueriaRepositorio.getCitasPeluqueria()

    var hayCitaActual = false
    var citaActual = CitaPeluqueria()
}