package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*

class AjustesEditarCitaViewModel : ViewModel() {

    private val citaPeluqueriaRepositorio = CitaPeluqueriaRepository.get()

    var citaPeluqueria = CitaPeluqueria()
    var algoHaSidoEditado = false
    var citaAntigua = false
    var citaYaCargada = false

    // Primera inicializacion de la cita
    fun cargarCitaPeluqueria(citaPeluqueria: CitaPeluqueria) {
        if (!citaYaCargada) {
            this.citaPeluqueria = citaPeluqueria
            citaYaCargada = true
        }
    }

    fun actualizarCitaPeluqueria(citaPeluqueria: CitaPeluqueria) {
        citaPeluqueriaRepositorio.updateCitaPeluqueria(citaPeluqueria)
    }

    fun guardarCitaPeluqueria(citaPeluqueria: CitaPeluqueria) {
        citaPeluqueriaRepositorio.addCitaPeluqueria(citaPeluqueria)
    }

    fun borrarCitaPeluqueria(citaPeluqueria: CitaPeluqueria) {
        citaPeluqueriaRepositorio.deleteCitaPeluqueria(citaPeluqueria)
    }

    fun borrarCitaActual(idCitaActual: UUID) {
        citaPeluqueriaRepositorio.deleteCitaActualPeluqueriaPorID(idCitaActual)
    }

    fun convertirCitaALong(): Long {
        return this.citaPeluqueria.fecha.time
    }
}