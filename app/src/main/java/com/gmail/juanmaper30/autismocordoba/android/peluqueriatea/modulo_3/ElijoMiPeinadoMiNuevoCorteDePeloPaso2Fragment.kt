package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.modulo_3

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.Avatar
import com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.R

private const val TAG = "ElijoMiPeinadoNuevoCor2"
private const val ARG_OPCION_CHICO_ELEGIDA = "opcion_chico_elegida"
private const val ARG_OPCION_PEINADO_ELEGIDA = "opcion_peinado_elegida"
private const val KEY_COLOR_ELEGIDO = "opcion_color_temporal"


class ElijoMiPeinadoMiNuevoCorteDePeloPaso2Fragment : Fragment() {

    interface Callbacks {
        fun elijoMiPeinadoNuevoCorteDePeloMontarResultado(opcionColor: Int)
    }

    private var callbacks: Callbacks? = null

    private lateinit var atrasButton: ImageButton
    private lateinit var siguienteButton: ImageButton
    private lateinit var colorRubioButton: ImageButton
    private lateinit var colorNegroButton: ImageButton
    private lateinit var colorMarronButton: ImageButton

    private lateinit var personajeImageView: ImageView

    private var opcionChicoElegida: Boolean = true
    private var opcionPeinadoElegida: Int = 2

    // Me creo el viewmodel para guardar el peinado escogido
    private val NuevoCortePaso2ViewModel: ElijoMiPeinadoMiNuevoCorteDePeloPaso2ViewModel by lazy {
        ViewModelProvider(this).get(ElijoMiPeinadoMiNuevoCorteDePeloPaso2ViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* Aqui obtengo el dato tipo boolean que le paso a newInstance desde MainActivity para
       saber si he de mostrar un chico o una chica, y el dato tipo int que representa la opcion
        de peinado escogida en el paso anterior*/
        opcionChicoElegida = arguments?.getSerializable(ARG_OPCION_CHICO_ELEGIDA) as Boolean
        opcionPeinadoElegida = arguments?.getSerializable(ARG_OPCION_PEINADO_ELEGIDA) as Int
        Log.i(
            TAG, "Fragmento $TAG creado con opcion mostrar chico = $opcionChicoElegida " +
                "y opcion peinado = $opcionPeinadoElegida")

        val opcionColorActual = savedInstanceState?.getInt(KEY_COLOR_ELEGIDO, 2) ?: 2
        Log.i(TAG, "Valor cogido de savedInstanceState o defecto: $opcionColorActual")

        NuevoCortePaso2ViewModel.opcionColorElegida = opcionColorActual
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(
            R.layout.fragment_elijo_mi_peinado_mi_nuevo_corte_de_pelo_paso2,
            container, false)

        atrasButton = view.findViewById(R.id.elijoMiPeinado_atrasButton)
        siguienteButton = view.findViewById(R.id.elijoMiPeinado_siguienteButton)
        colorRubioButton = view.findViewById(R.id.seleccionColorRubioImageButton)
        colorMarronButton = view.findViewById(R.id.seleccionColorMarronImageButton)
        colorNegroButton = view.findViewById(R.id.seleccionColorNegroImageButton)

        personajeImageView = view.findViewById(R.id.elijoMiPeinado_Paso2ImageView)

        /* Muestro el personaje chico o chica con el peinado correspondiente, asi como los botones
        con el peinado elegido */
        actualizarPersonaje()
        actualizarPeinadosDeLosBotones()
        marcarBotonSeleccionado()

        return view
    }

    override fun onStart() {
        super.onStart()

        /* El boton de atras llama al boton de retroceso, el siguiente usa la callback para decirle
        a MainActivity que monte el resultado de este modulo. Le paso el color que se ha elegido
         */
        atrasButton.setOnClickListener {
            Log.i(TAG, "Saliendo de fragmento Elijo Mi Peinado Mi Nuevo Corte De Pelo Paso 2")
            activity?.onBackPressed()
        }

        siguienteButton.setOnClickListener {
            callbacks?.elijoMiPeinadoNuevoCorteDePeloMontarResultado(NuevoCortePaso2ViewModel.opcionColorElegida)
        }

        /* Listener en el boton del color rubio. Funciona solo si no esta marcado, y al darle,
        poner el color a su correspondiente, y actualiza el personaje y el marcado de botones
         */
        colorRubioButton.setOnClickListener {
            if (NuevoCortePaso2ViewModel.opcionColorElegida != 1) {
                Log.d(
                    TAG, "Pulsado boton de color rubio con opcionColor = " +
                        "${NuevoCortePaso2ViewModel.opcionColorElegida}")

                NuevoCortePaso2ViewModel.opcionColorElegida = 1
                actualizarPersonaje()
                marcarBotonSeleccionado()
            }
        }

        colorMarronButton.setOnClickListener {
            if (NuevoCortePaso2ViewModel.opcionColorElegida != 2) {
                Log.d(
                    TAG, "Pulsado boton de color castaño con opcionColor = " +
                        "${NuevoCortePaso2ViewModel.opcionColorElegida}")

                NuevoCortePaso2ViewModel.opcionColorElegida = 2
                actualizarPersonaje()
                marcarBotonSeleccionado()
            }
        }

        colorNegroButton.setOnClickListener {
            if (NuevoCortePaso2ViewModel.opcionColorElegida != 3) {
                Log.d(
                    TAG, "Pulsado boton de color negro con opcionColor = " +
                        "${NuevoCortePaso2ViewModel.opcionColorElegida}")

                NuevoCortePaso2ViewModel.opcionColorElegida = 3
                actualizarPersonaje()
                marcarBotonSeleccionado()
            }
        }


    }

    // Por si el OS destruye el proceso, guardo el color que habia elegido
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(
            TAG, "Guardando saveInstanceState de $TAG con opcionColor: " +
                "${NuevoCortePaso2ViewModel.opcionColorElegida}")
        savedInstanceState.putInt(KEY_COLOR_ELEGIDO, NuevoCortePaso2ViewModel.opcionColorElegida)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Destruyendo fragmento Elijo mi peinado Mi Nuevo Corte de Pelo Paso 2")
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }




    fun actualizarPersonaje() {

        val idRecursoPersonajeAMostrar: Int = Avatar(
            opcionChicoElegida, opcionPeinadoElegida,
            NuevoCortePaso2ViewModel.opcionColorElegida
        ).idRecursoAsociado()

        personajeImageView.setImageResource(idRecursoPersonajeAMostrar)
    }

    fun actualizarPeinadosDeLosBotones() {
        if (opcionChicoElegida) {
            when (opcionPeinadoElegida) {
                1 -> {
                    colorRubioButton.setImageResource(R.drawable.ic_peinado_chico_corto_rubio)
                    colorMarronButton.setImageResource(R.drawable.ic_peinado_chico_corto_marron)
                    colorNegroButton.setImageResource(R.drawable.ic_peinado_chico_corto_negro)
                }

                2 -> {
                    colorRubioButton.setImageResource(R.drawable.ic_peinado_chico_medio_rubio)
                    colorMarronButton.setImageResource(R.drawable.ic_peinado_chico_medio_marron)
                    colorNegroButton.setImageResource(R.drawable.ic_peinado_chico_medio_negro)
                }

                3 -> {
                    colorRubioButton.setImageResource(R.drawable.ic_peinado_chico_largo_rubio)
                    colorMarronButton.setImageResource(R.drawable.ic_peinado_chico_largo_marron)
                    colorNegroButton.setImageResource(R.drawable.ic_peinado_chico_largo_negro)
                }
            }
        } else {
            when (opcionPeinadoElegida) {
                1 -> {
                    colorRubioButton.setImageResource(R.drawable.ic_peinado_chica_corto_rubio)
                    colorMarronButton.setImageResource(R.drawable.ic_peinado_chica_corto_marron)
                    colorNegroButton.setImageResource(R.drawable.ic_peinado_chica_corto_negro)
                }

                2 -> {
                    colorRubioButton.setImageResource(R.drawable.ic_peinado_chica_medio_rubio)
                    colorMarronButton.setImageResource(R.drawable.ic_peinado_chica_medio_marron)
                    colorNegroButton.setImageResource(R.drawable.ic_peinado_chica_medio_negro)
                }

                3 -> {
                    colorRubioButton.setImageResource(R.drawable.ic_peinado_chica_largo_rubio)
                    colorMarronButton.setImageResource(R.drawable.ic_peinado_chica_largo_marron)
                    colorNegroButton.setImageResource(R.drawable.ic_peinado_chica_largo_negro)
                }
            }
        }
    }

    /* Funcion que actualiza que boton se marca segun la opcion escogida */
    fun marcarBotonSeleccionado() {
        when (NuevoCortePaso2ViewModel.opcionColorElegida) {
            1 -> {
                colorRubioButton.setBackgroundResource(R.drawable.button_ajustes_chicochica_seleccionado)
                colorMarronButton.setBackgroundResource(R.drawable.button_ajustes_chicochica_no_seleccionado)
                colorNegroButton.setBackgroundResource(R.drawable.button_ajustes_chicochica_no_seleccionado)
            }

            2 -> {
                colorRubioButton.setBackgroundResource(R.drawable.button_ajustes_chicochica_no_seleccionado)
                colorMarronButton.setBackgroundResource(R.drawable.button_ajustes_chicochica_seleccionado)
                colorNegroButton.setBackgroundResource(R.drawable.button_ajustes_chicochica_no_seleccionado)
            }

            3 -> {
                colorRubioButton.setBackgroundResource(R.drawable.button_ajustes_chicochica_no_seleccionado)
                colorMarronButton.setBackgroundResource(R.drawable.button_ajustes_chicochica_no_seleccionado)
                colorNegroButton.setBackgroundResource(R.drawable.button_ajustes_chicochica_seleccionado)
            }
        }
    }



    /* Companion object, se puede llamar a la funcion como si fuese un metodo */
    companion object {
        /* Crea instancias de ElijoMiPeinadoMiNuevoCorteDePeloPaso2, recogiendo el valor que se le haya
        pasado como argumento, es decir, el boolean que indica si he de mostrar un chico o una chica y
        el tipo de peinado elegido
         */
        fun newInstance(opcionChicoElegida: Boolean, opcionPeinadoElegida: Int): ElijoMiPeinadoMiNuevoCorteDePeloPaso2Fragment {
            val args = Bundle().apply {
                putSerializable(ARG_OPCION_CHICO_ELEGIDA, opcionChicoElegida)
                putSerializable(ARG_OPCION_PEINADO_ELEGIDA, opcionPeinadoElegida)
            }
            return ElijoMiPeinadoMiNuevoCorteDePeloPaso2Fragment()
                .apply {
                arguments = args
            }
        }
    }
}