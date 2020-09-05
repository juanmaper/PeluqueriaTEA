package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.modulo_5

import androidx.lifecycle.ViewModel
import com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.CitaPeluqueria
import com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.database.CitaPeluqueriaRepository

class AjustesGestionCitasViewModel : ViewModel() {

    private val citaPeluqueriaRepositorio =
        CitaPeluqueriaRepository.get()
    val listaCitasPeluqueriaLiveData = citaPeluqueriaRepositorio.getCitasPeluqueria()

    var hayCitaActual = false
    var citaActual =
        CitaPeluqueria()
}