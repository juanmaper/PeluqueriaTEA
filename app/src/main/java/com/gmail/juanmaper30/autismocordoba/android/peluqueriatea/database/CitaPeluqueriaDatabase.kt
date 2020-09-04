package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.modelos.CitaPeluqueria

@Database(entities = [CitaPeluqueria::class], version = 1, exportSchema = false)
@TypeConverters(CitaPeluqueriaTypeConverters::class)
abstract class CitaPeluqueriaDatabase : RoomDatabase() {


    abstract fun citaPeluqueriaDao(): CitaPeluqueriaDao
    
}