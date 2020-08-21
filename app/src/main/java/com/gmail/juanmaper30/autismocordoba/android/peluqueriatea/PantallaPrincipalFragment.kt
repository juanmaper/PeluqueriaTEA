package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment

private const val TAG = "PantallaPrincipalFragment"

class PantallaPrincipalFragment : Fragment() {

    // Interfaz para que MainActivity pueda hostear el fragmento y hacer callbacks
    interface Callbacks {
        fun moduloConsejosSeleccionado()
        fun moduloVamosPeluqueriaSeleccionado()
        fun moduloElijoMiPeinadoSeleccionado()
        fun moduloJuegoAsociacionSonidosSeleccionado()
        fun moduloAjustesSeleccionado()
        fun moduloCreditosSeleccionado()
    }

    private var callbacks: Callbacks? = null


    private lateinit var consejosButton: ImageButton
    private lateinit var vamosPeluqueriaButton: ImageButton
    private lateinit var elijoMiPeinadoButton: ImageButton
    private lateinit var juegoButton: ImageButton
    private lateinit var ajustesButton: ImageButton
    private lateinit var creditosButton: ImageButton

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //Hago un inflate al xml de la pantalla principal. Cojo una referencia a los botones
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pantalla_principal, container, false)

        consejosButton = view.findViewById(R.id.pantallaPrincipal_consejosButton) as ImageButton
        vamosPeluqueriaButton = view.findViewById(R.id.pantallaPrincipal_vamosPeluqueriaButton) as ImageButton
        elijoMiPeinadoButton = view.findViewById(R.id.pantallaPrincipal_elijoMiPeinadoButton) as ImageButton
        ajustesButton = view.findViewById(R.id.pantallaPrincipal_ajustesButton) as ImageButton
        juegoButton = view.findViewById(R.id.pantallaPrincipal_juegosButton) as ImageButton
        creditosButton = view.findViewById(R.id.pantallaPrincipal_creditosButton) as ImageButton

        return view
    }

    // Aqui pongo los listeners a los botones
    override fun onStart() {
        super.onStart()

        consejosButton.setOnClickListener {
            callbacks?.moduloConsejosSeleccionado()
        }

        vamosPeluqueriaButton.setOnClickListener {
            callbacks?.moduloVamosPeluqueriaSeleccionado()
        }

        elijoMiPeinadoButton.setOnClickListener {
            callbacks?.moduloElijoMiPeinadoSeleccionado()
        }

        juegoButton.setOnClickListener {
            callbacks?.moduloJuegoAsociacionSonidosSeleccionado()
        }

        ajustesButton.setOnLongClickListener{
            callbacks?.moduloAjustesSeleccionado()
            true
        }

        creditosButton.setOnLongClickListener {
            callbacks?.moduloCreditosSeleccionado()
            true
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }
}