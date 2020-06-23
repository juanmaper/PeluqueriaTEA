package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment

private const val TAG = "ElijoMiPeinadoNuevoCor1"
private const val ARG_OPCION_CHICO_ELEGIDA = "opcion_chico_elegida"

class ElijoMiPeinadoMiNuevoCorteDePeloPaso1 : Fragment() {

    interface Callbacks {
        fun elijoMiPeinadoNuevoCorteDePeloMontarPaso2(opcionPeinado: Int)
    }

    private var callbacks: Callbacks? = null

    private lateinit var atrasButton: ImageButton
    private lateinit var siguienteButton: ImageButton
    private lateinit var peinadoCortoButton: ImageButton
    private lateinit var peinadoMedioButton: ImageButton
    private lateinit var peinadoLargoButton: ImageButton

    private lateinit var personajeImageView: ImageView

    private var opcionChicoElegida: Boolean = true
    private var opcionPeinadoElegida: Int = 2

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* Aqui obtengo el dato tipo boolean que le paso a newInstance desde MainActivity para
       saber si he de mostrar un chico o una chica */
        opcionChicoElegida = arguments?.getSerializable(ARG_OPCION_CHICO_ELEGIDA) as Boolean
        Log.i(TAG, "Fragmento $TAG creado con opcion mostrar chico = $opcionChicoElegida")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_elijo_mi_peinado_mi_nuevo_corte_de_pelo_paso1,
            container, false)

        atrasButton = view.findViewById(R.id.elijoMiPeinado_atrasButton)
        siguienteButton = view.findViewById(R.id.elijoMiPeinado_siguienteButton)
        peinadoCortoButton = view.findViewById(R.id.seleccionPeinadoCortoImageButton)
        peinadoMedioButton = view.findViewById(R.id.seleccionPeinadoMedioImageButton)
        peinadoLargoButton = view.findViewById(R.id.seleccionPeinadoLargoImageButton)

        personajeImageView = view.findViewById(R.id.elijoMiPeinado_Paso1ImageView)

        /* Si no es un chico, cambio los botones e imageView para mostrar peinados y avatar de
        chica */
        if (!opcionChicoElegida) {
            peinadoCortoButton.setImageResource(R.drawable.ic_peinado_chica_corto_marron)
            peinadoMedioButton.setImageResource(R.drawable.ic_peinado_chica_medio_marron)
            peinadoLargoButton.setImageResource(R.drawable.ic_peinado_chica_largo_marron)
        }

        actualizarPersonaje()
        marcarBotonSeleccionado()

        return view
    }

    override fun onStart() {
        super.onStart()

        /* El boton de atras llama al boton de retroceso, el siguiente usa la callback para decirle
        a MainActivity que monte el paso 2 de este modulo. Le paso el peinado que se ha elegido
         */
        atrasButton.setOnClickListener {
            Log.i(TAG, "Saliendo de fragmento Elijo Mi Peinado Mi Nuevo Corte De Pelo Paso 1")
            activity?.onBackPressed()
        }

        siguienteButton.setOnClickListener {
            callbacks?.elijoMiPeinadoNuevoCorteDePeloMontarPaso2(opcionPeinadoElegida)
        }

        /* Listener en el boton del peinado corto. Solo funciona si no esta ya marcado, y en ese caso,
            se marca y cambia el personaje mostrado al adecuado, con pelo corto, dependiendo si es chico
            o chica
         */
        peinadoCortoButton.setOnClickListener {
            if (opcionPeinadoElegida != 1) {
                Log.d(TAG, "Pulsado boton de peinado corto con opcionPeinadoElegida = $opcionPeinadoElegida")

                opcionPeinadoElegida = 1
                marcarBotonSeleccionado()
                actualizarPersonaje()
            }
        }

        peinadoMedioButton.setOnClickListener {
            if (opcionPeinadoElegida != 2) {
                Log.d(TAG, "Pulsado boton de peinado medio con opcionPeinadoElegida = $opcionPeinadoElegida")

                opcionPeinadoElegida = 2
                marcarBotonSeleccionado()
                actualizarPersonaje()
            }
        }

        peinadoLargoButton.setOnClickListener {
            if (opcionPeinadoElegida != 3) {
                Log.d(TAG, "Pulsado boton de peinado largo con opcionPeinadoElegida = $opcionPeinadoElegida")

                opcionPeinadoElegida = 3
                marcarBotonSeleccionado()
                actualizarPersonaje()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Destruyendo fragmento Elijo mi peinado Mi Nuevo Corte de Pelo Paso 1 ")
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }


    /* Funcion que actualiza que boton se marca segun la opcion escogida */
    fun marcarBotonSeleccionado() {
        when (opcionPeinadoElegida) {
            1 -> {
                peinadoCortoButton.setBackgroundResource(R.drawable.button_ajustes_chicochica_seleccionado)
                peinadoMedioButton.setBackgroundResource(R.drawable.button_ajustes_chicochica_no_seleccionado)
                peinadoLargoButton.setBackgroundResource(R.drawable.button_ajustes_chicochica_no_seleccionado)
            }

            2 -> {
                peinadoCortoButton.setBackgroundResource(R.drawable.button_ajustes_chicochica_no_seleccionado)
                peinadoMedioButton.setBackgroundResource(R.drawable.button_ajustes_chicochica_seleccionado)
                peinadoLargoButton.setBackgroundResource(R.drawable.button_ajustes_chicochica_no_seleccionado)
            }

            3 -> {
                peinadoCortoButton.setBackgroundResource(R.drawable.button_ajustes_chicochica_no_seleccionado)
                peinadoMedioButton.setBackgroundResource(R.drawable.button_ajustes_chicochica_no_seleccionado)
                peinadoLargoButton.setBackgroundResource(R.drawable.button_ajustes_chicochica_seleccionado)
            }
        }
    }

    /* Funcion que actualiza el personaje de la pantalla en funcion de la opcion de peinado escogida,
    asi como si es chico o chica
     */
    fun actualizarPersonaje() {
        if (opcionChicoElegida) {
            when (opcionPeinadoElegida) {
                1 -> personajeImageView.setImageResource(R.drawable.ic_personaje_peinado_chico_corto_marron)
                2 -> personajeImageView.setImageResource(R.drawable.ic_personaje_peinado_chico_medio_marron)
                3 -> personajeImageView.setImageResource(R.drawable.ic_personaje_peinado_chico_largo_marron)
            }
        } else {
            when (opcionPeinadoElegida) {
                1 -> personajeImageView.setImageResource(R.drawable.ic_personaje_peinado_chica_corto_marron)
                2 -> personajeImageView.setImageResource(R.drawable.ic_personaje_peinado_chica_medio_marron)
                3 -> personajeImageView.setImageResource(R.drawable.ic_personaje_peinado_chica_largo_marron)
            }
        }
    }


    /* Companion object, se puede llamar a la funcion como si fuese un metodo */
    companion object {
        /* Crea instancias de ElijoMiPeinadoMiNuevoCorteDePeloPaso1, recogiendo el valor que se le haya
        pasado como argumento, es decir, el boolean que indica si he de mostrar un chico o una chica
         */
        fun newInstance(opcionChicoElegida: Boolean): ElijoMiPeinadoMiNuevoCorteDePeloPaso1 {
            val args = Bundle().apply {
                putSerializable(ARG_OPCION_CHICO_ELEGIDA, opcionChicoElegida)
            }
            return ElijoMiPeinadoMiNuevoCorteDePeloPaso1().apply {
                arguments = args
            }
        }
    }
}