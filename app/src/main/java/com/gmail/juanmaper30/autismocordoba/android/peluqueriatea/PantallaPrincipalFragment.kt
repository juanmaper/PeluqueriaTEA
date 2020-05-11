package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment

class PantallaPrincipalFragment : Fragment() {

    // Interfaz para que MainActivity pueda hostear el fragmento y hacer callbacks
    interface Callbacks {
        fun moduloConsejosSeleccionado()
    }

    private var callbacks: Callbacks? = null


    private lateinit var consejosButton: ImageButton

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

        return view
    }

    // Aqui pongo los listeners a los botones
    override fun onStart() {
        super.onStart()

        consejosButton.setOnClickListener {
            callbacks?.moduloConsejosSeleccionado()
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }
}