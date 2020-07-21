package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.CitaPeluqueria
import java.util.*

// Dao = Data Access Object
@Dao
interface CitaPeluqueriaDao {

    @Query("SELECT * FROM citapeluqueria")
    fun getCitasPeluqueria(): LiveData<List<CitaPeluqueria>>

    @Query("SELECT * FROM citapeluqueria WHERE id=(:id)")
    fun getCitaPeluqueria(id: UUID): LiveData<CitaPeluqueria?>
}