package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.modulo_3

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.modelos.Avatar
import com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.R

private const val TAG = "ElijoMiPeinadoNuevoResu"
private const val ARG_OPCION_CHICO_ELEGIDA = "opcion_chico_elegida"
private const val ARG_OPCION_PEINADO_ELEGIDA = "opcion_peinado_elegida"
private const val ARG_OPCION_COLOR_ELEGIDA = "opcion_color_elegida"

class ElijoMiPeinadoMiNuevoCorteDePeloResultadoFragment : Fragment() {

    interface Callbacks {
        fun elijoMiPeinadoNuevoCorteDePeloNuevoAvatarCreado(avatarCreado: Avatar)
    }


    private var callbacks: Callbacks? = null

    private lateinit var atrasButton: ImageButton
    private lateinit var siguienteButton: ImageButton
    private lateinit var mensajeTextView: TextView
    private lateinit var personajeImageView: ImageView

    private var opcionChicoElegida: Boolean = true
    private var opcionPeinadoElegida: Int = 2
    private var opcionColorElegida: Int = 2


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* Aqui obtengo el dato tipo boolean que le paso a newInstance desde MainActivity para
       saber si he de mostrar un chico o una chica, el dato tipo int que representa la opcion
        de peinado escogida en el paso anterior y el color del pelo*/
        opcionChicoElegida = arguments?.getSerializable(ARG_OPCION_CHICO_ELEGIDA) as Boolean
        opcionPeinadoElegida = arguments?.getSerializable(ARG_OPCION_PEINADO_ELEGIDA) as Int
        opcionColorElegida = arguments?.getSerializable(ARG_OPCION_COLOR_ELEGIDA) as Int
        Log.i(
            TAG, "Fragmento $TAG creado con opcion mostrar chico = $opcionChicoElegida " +
                ", opcion peinado = $opcionPeinadoElegida y opcion color = $opcionColorElegida")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_elijo_mi_peinado_mi_nuevo_corte_de_pelo_resultado,
            container, false)

        atrasButton = view.findViewById(R.id.elijoMiPeinado_atrasButton)
        siguienteButton = view.findViewById(R.id.elijoMiPeinado_siguienteButton)
        mensajeTextView = view.findViewById(R.id.elijoMiPeinado_MiNuevoCorteDePeloResultadoTextView)
        personajeImageView = view.findViewById(R.id.elijoMiPeinado_MiNuevoCorteDePeloResultadoImageView)

        if (opcionChicoElegida) {
            mensajeTextView.setText(R.string.elijoMiPeinadoNuevo_chicoResultadoTextView)
        } else {
            mensajeTextView.setText(R.string.elijoMiPeinadoNuevo_chicaResultadoTextView)
        }

        actualizarPersonaje()

        return view
    }

    override fun onStart() {
        super.onStart()

        /* El boton de atras llama al boton de retroceso, el siguiente usa la callback para decirle
        a MainActivity que guarde el avatar en la lista. Le paso un avatar
         */
        atrasButton.setOnClickListener {
            Log.i(TAG, "Saliendo de fragmento Elijo Mi Peinado Mi Nuevo Corte Resultado")
            activity?.onBackPressed()
        }

        siguienteButton.setOnClickListener {
            val nuevoAvatar =
                Avatar(
                    opcionChicoElegida,
                    opcionPeinadoElegida,
                    opcionColorElegida
                )
            callbacks?.elijoMiPeinadoNuevoCorteDePeloNuevoAvatarCreado(nuevoAvatar)
        }
    }









    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Destruyendo fragmento Elijo mi peinado Mi Nuevo Corte Resultado")
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    fun actualizarPersonaje() {

        val idRecursoPersonajeAMostrar: Int = Avatar(
            opcionChicoElegida, opcionPeinadoElegida,
            opcionColorElegida
        ).idRecursoAsociado()

        personajeImageView.setImageResource(idRecursoPersonajeAMostrar)
    }

    /* Companion object, se puede llamar a la funcion como si fuese un metodo */
    companion object {
        /* Crea instancias de ElijoMiPeinadoMiNuevoCorteDePeloResultado, recogiendo el valor que se le haya
        pasado como argumento, es decir, el boolean que indica si he de mostrar un chico o una chica,
        el tipo de peinado elegido y el color del pelo elegido
         */
        fun newInstance(opcionChicoElegida: Boolean, opcionPeinadoElegida: Int, opcionColorElegida: Int)
                : ElijoMiPeinadoMiNuevoCorteDePeloResultadoFragment {
            val args = Bundle().apply {
                putSerializable(ARG_OPCION_CHICO_ELEGIDA, opcionChicoElegida)
                putSerializable(ARG_OPCION_PEINADO_ELEGIDA, opcionPeinadoElegida)
                putSerializable(ARG_OPCION_COLOR_ELEGIDA, opcionColorElegida)
            }
            return ElijoMiPeinadoMiNuevoCorteDePeloResultadoFragment()
                .apply {
                arguments = args
            }
        }
    }
}