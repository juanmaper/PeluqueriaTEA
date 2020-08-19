package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.CitaPeluqueria
import java.util.*

// Dao = Data Access Object
@Dao
interface CitaPeluqueriaDao {

    @Query("SELECT * FROM citapeluqueria ORDER BY fecha DESC")
    fun getCitasPeluqueria(): LiveData<List<CitaPeluqueria>>

    @Query("SELECT * FROM citapeluqueria WHERE id=(:id)")
    fun getCitaPeluqueria(id: UUID): LiveData<CitaPeluqueria?>

    @Update
    fun updateCitaPeluqueria(citaPeluqueria: CitaPeluqueria)

    @Insert
    fun addCitaPeluqueria(citaPeluqueria: CitaPeluqueria)

    @Delete
    fun deleteCitaPeluqueria(citaPeluqueria: CitaPeluqueria)

    @Query("DELETE FROM citapeluqueria WHERE id=(:idCitaActual)")
    fun deleteCitaActualPeluqueria(idCitaActual: UUID)
}