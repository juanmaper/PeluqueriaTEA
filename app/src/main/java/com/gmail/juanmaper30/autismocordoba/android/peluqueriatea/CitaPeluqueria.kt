package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity
data class CitaPeluqueria(@PrimaryKey val id: UUID = UUID.randomUUID(),
                          var fecha: Date = Date(),
                          var comentario: String = ""): Serializable {


}