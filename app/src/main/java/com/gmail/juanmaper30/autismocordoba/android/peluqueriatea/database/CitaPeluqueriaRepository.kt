package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.modelos.CitaPeluqueria
import java.util.*
import java.util.concurrent.Executors

private const val NOMBRE_BASE_DE_DATOS = "citaPeluqueria-database"

class CitaPeluqueriaRepository private constructor(context: Context){

    private val database: CitaPeluqueriaDatabase = Room.databaseBuilder(
        context.applicationContext,
        CitaPeluqueriaDatabase::class.java,
        NOMBRE_BASE_DE_DATOS
    ).build()

    private val citaPeluqueriaDao = database.citaPeluqueriaDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getCitasPeluqueria(): LiveData<List<CitaPeluqueria>> = citaPeluqueriaDao.getCitasPeluqueria()

    fun getCitaPeluqueria(id: UUID): LiveData<CitaPeluqueria?> = citaPeluqueriaDao.getCitaPeluqueria(id)

    fun updateCitaPeluqueria(citaPeluqueria: CitaPeluqueria) {
        executor.execute {
            citaPeluqueriaDao.updateCitaPeluqueria(citaPeluqueria)
        }
    }

    fun addCitaPeluqueria(citaPeluqueria: CitaPeluqueria) {
        executor.execute {
            citaPeluqueriaDao.addCitaPeluqueria(citaPeluqueria)
        }
    }

    fun deleteCitaPeluqueria(citaPeluqueria: CitaPeluqueria) {
        executor.execute {
            citaPeluqueriaDao.deleteCitaPeluqueria(citaPeluqueria)
        }
    }

    fun deleteCitaActualPeluqueriaPorID(idCitaActualPeluqueria: UUID) {
        executor.execute {
            citaPeluqueriaDao.deleteCitaActualPeluqueria(idCitaActualPeluqueria)
        }
    }

    companion object {
        private var INSTANCE: CitaPeluqueriaRepository? = null

        fun inicializar(context: Context) {
            if (INSTANCE == null) {
                INSTANCE =
                    CitaPeluqueriaRepository(
                        context
                    )
            }
        }

        fun get(): CitaPeluqueriaRepository {
            return INSTANCE
                ?:
                    throw IllegalStateException("CitaPeluqueriaRepository debe ser inicializado")
        }
    }
}