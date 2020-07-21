package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.database.CitaPeluqueriaDatabase
import java.util.*

private const val NOMBRE_BASE_DE_DATOS = "citaPeluqueria-database"

class CitaPeluqueriaRepository private constructor(context: Context){

    private val database: CitaPeluqueriaDatabase = Room.databaseBuilder(
        context.applicationContext,
        CitaPeluqueriaDatabase::class.java,
        NOMBRE_BASE_DE_DATOS
    ).build()

    private val citaPeluqueriaDao = database.citaPeluqueriaDao()

    fun getCitasPeluqueria(): LiveData<List<CitaPeluqueria>> = citaPeluqueriaDao.getCitasPeluqueria()

    fun getCitaPeluqueria(id: UUID): LiveData<CitaPeluqueria?> = citaPeluqueriaDao.getCitaPeluqueria(id)

    companion object {
        private var INSTANCE: CitaPeluqueriaRepository? = null

        fun inicializar(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CitaPeluqueriaRepository(context)
            }
        }

        fun get(): CitaPeluqueriaRepository {
            return INSTANCE ?:
                    throw IllegalStateException("CitaPeluqueriaRepository debe ser inicializado")
        }
    }
}