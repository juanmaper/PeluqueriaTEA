package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import android.app.Application
import com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.database.CitaPeluqueriaRepository

//Subclase para inicializar el repositorio en cuanto la app este lista
class PeluTEAApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CitaPeluqueriaRepository.inicializar(this)
    }
}