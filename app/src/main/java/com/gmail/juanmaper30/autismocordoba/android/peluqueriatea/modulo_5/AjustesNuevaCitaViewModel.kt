package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.modulo_5

import androidx.lifecycle.ViewModel
import com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.database.CitaPeluqueriaRepository
import java.util.*

class AjustesNuevaCitaViewModel : ViewModel() {

    private val citaPeluqueriaRepositorio =
        CitaPeluqueriaRepository.get()

    var nuevaCitaPeluqueria =
        CitaPeluqueria()
    var algoHaSidoEditado = false

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
        return this.nuevaCitaPeluqueria.fecha.time
    }
}