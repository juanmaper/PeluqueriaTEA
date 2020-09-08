package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.modulo_3

import com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.R

class Avatar(var soyChico: Boolean = true, var opcionPeinado: Int = 2, var opcionColor: Int = 2) {

    /* Constructor de la clase pasandole un codigo numerico que representa la eleccion
        de opciones
     */
    constructor(codigo: Int): this() {
        /* Los codigos tienen 3 numeros:
            Primer numero: 1 (el avatar es chico) o 2 (el avatar es chica).
            Segundo numero: 1, 2 o 3. Representa la opcion de peinado (corto, medio o largo)
            Tercer numero: 1, 2 o 3. Representa la opcion de color (rubio, marron o negro)
         */
        var numeroSoyChico = codigo / 100
        var auxiliar = codigo % 100

        var numeroPeinado = auxiliar / 10
        var numeroColor = auxiliar % 10

        this.soyChico = numeroSoyChico == 1
        this.opcionPeinado = numeroPeinado
        this.opcionColor = numeroColor
    }

    /* Calcula el codigo asociado al avatar, que se guardara en la lista de los avatares como
    entero.
     */
    fun getCodigo(): Int {
        var codigo =
            if (soyChico) {
                100
            } else {
                200
            }

        codigo += opcionPeinado * 10 + opcionColor

        return codigo
    }

    /* Retorna el recurso asociado al avatar
       Devolvere el indice del recurso a usar, y luego lo pondre en el personaje. Para ello, primero
        miro si se esta mostrando un chico o una chica, y luego en funcion del peinado escogido y
        opcion de color, devuelvo un id de recurso u otro
         */
    fun idRecursoAsociado(): Int {
        return if (soyChico) {
            when (opcionPeinado) {
                1 -> when (opcionColor) {
                    1 -> R.drawable.ic_personaje_peinado_chico_corto_rubio
                    2 -> R.drawable.ic_personaje_peinado_chico_corto_marron
                    3 -> R.drawable.ic_personaje_peinado_chico_corto_negro
                    else -> R.drawable.ic_personaje_peinado_chico_corto_marron
                }

                2 -> when (opcionColor) {
                    1 -> R.drawable.ic_personaje_peinado_chico_medio_rubio
                    2 -> R.drawable.ic_personaje_peinado_chico_medio_marron
                    3 -> R.drawable.ic_personaje_peinado_chico_medio_negro
                    else -> R.drawable.ic_personaje_peinado_chico_medio_marron
                }

                3 -> when (opcionColor) {
                    1 -> R.drawable.ic_personaje_peinado_chico_largo_rubio
                    2 -> R.drawable.ic_personaje_peinado_chico_largo_marron
                    3 -> R.drawable.ic_personaje_peinado_chico_largo_negro
                    else -> R.drawable.ic_personaje_peinado_chico_largo_marron
                }
                else -> R.drawable.ic_personaje_peinado_chico_medio_marron
            }
        } else {
            when (opcionPeinado) {
                1 -> when (opcionColor) {
                    1 -> R.drawable.ic_personaje_peinado_chica_corto_rubio
                    2 -> R.drawable.ic_personaje_peinado_chica_corto_marron
                    3 -> R.drawable.ic_personaje_peinado_chica_corto_negro
                    else -> R.drawable.ic_personaje_peinado_chica_corto_marron
                }

                2 -> when (opcionColor) {
                    1 -> R.drawable.ic_personaje_peinado_chica_medio_rubio
                    2 -> R.drawable.ic_personaje_peinado_chica_medio_marron
                    3 -> R.drawable.ic_personaje_peinado_chica_medio_negro
                    else -> R.drawable.ic_personaje_peinado_chica_medio_marron
                }

                3 -> when (opcionColor) {
                    1 -> R.drawable.ic_personaje_peinado_chica_largo_rubio
                    2 -> R.drawable.ic_personaje_peinado_chica_largo_marron
                    3 -> R.drawable.ic_personaje_peinado_chica_largo_negro
                    else -> R.drawable.ic_personaje_peinado_chica_largo_marron
                }
                else -> R.drawable.ic_personaje_peinado_chica_medio_marron
            }
        }
    }
}