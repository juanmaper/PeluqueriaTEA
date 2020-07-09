package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

private const val TAG = "AjustesFragment"
private const val ARG_OPCION_CHICO_ELEGIDA = "opcion_chico_elegida"

class AjustesFragment : Fragment() {

    // Interfaz para comunicarme con MainActivity y decirle que cambie la opcion de chico o chica
    interface Callbacks {
        fun ajustesCambiarOpcionChicoChica(chicoElegido: Boolean)
        fun ajustesReiniciarListaAvataresGuardados()
    }

    private var callbacks: Callbacks? = null

    private var opcionChicoElegida: Boolean = true

    private lateinit var chicoButton: Button
    private lateinit var chicaButton: Button
    private lateinit var reiniciarListaAvataresButton: Button

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
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
        val view = inflater.inflate(R.layout.fragment_ajustes, container, false)

        chicoButton = view.findViewById(R.id.opcionChicoButton)
        chicaButton = view.findViewById(R.id.opcionChicaButton)
        reiniciarListaAvataresButton = view.findViewById(R.id.reiniciarListaAvataresButton)

        if (opcionChicoElegida){
            Log.i(TAG, "Mostrando boton de chico seleccionado en ajustes")
            chicoButton.setBackgroundResource(R.drawable.button_ajustes_chicochica_seleccionado)
            chicaButton.setBackgroundResource(R.drawable.button_ajustes_chicochica_no_seleccionado)
        } else {
            Log.i(TAG, "Mostrando boton de chica seleccionado en ajustes")
            chicoButton.setBackgroundResource(R.drawable.button_ajustes_chicochica_no_seleccionado)
            chicaButton.setBackgroundResource(R.drawable.button_ajustes_chicochica_seleccionado)
        }

        return view
    }

    override fun onStart() {
        super.onStart()

        chicoButton.setOnClickListener{
            if (opcionChicoElegida == false) {
                Log.i(TAG, "Pulsado boton de chico, cambiando fondos de boton y la opcion del" +
                        " viewmodel")
                callbacks?.ajustesCambiarOpcionChicoChica(true)

                chicoButton.setBackgroundResource(R.drawable.button_ajustes_chicochica_seleccionado)
                chicaButton.setBackgroundResource(R.drawable.button_ajustes_chicochica_no_seleccionado)

                opcionChicoElegida = true
            }
        }

        chicaButton.setOnClickListener{
            if (opcionChicoElegida) {
                callbacks?.ajustesCambiarOpcionChicoChica(false)
                Log.i(TAG, "Pulsado boton de chica, cambiando fondos de boton y la opcion del" +
                        " viewmodel")

                chicoButton.setBackgroundResource(R.drawable.button_ajustes_chicochica_no_seleccionado)
                chicaButton.setBackgroundResource(R.drawable.button_ajustes_chicochica_seleccionado)

                opcionChicoElegida = false
            }
        }

        reiniciarListaAvataresButton.setOnClickListener{
            callbacks?.ajustesReiniciarListaAvataresGuardados()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Destruyendo fragmento ajustes")
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    /* Companion object, se puede llamar a la funcion como si fuese un metodo */
    companion object {
        /* Crea instancias de AjustesFragment, recogiendo el valor que se le haya
        pasado como argumento, es decir, el boolean que indica si he de mostrar un chico o una chica
        para marcar el boton correspondiente
         */
        fun newInstance(opcionChicoElegida: Boolean): AjustesFragment {
            val args = Bundle().apply {
                putSerializable(ARG_OPCION_CHICO_ELEGIDA, opcionChicoElegida)
            }
            return AjustesFragment().apply {
                arguments = args
            }
        }
    }

}