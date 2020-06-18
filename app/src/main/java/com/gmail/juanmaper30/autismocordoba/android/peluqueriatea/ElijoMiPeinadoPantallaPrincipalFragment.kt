package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment

private const val TAG = "ElijoMiPeinadoPrincipal"

class ElijoMiPeinadoPantallaPrincipalFragment : Fragment() {

    // Interfaz para comunicarme con MainActivity y decirle que modulo montar
    interface Callbacks {
        fun elijoMiPeinadoMontarModuloMisCortesDePelo()
        fun elijoMiPeinadoMontarModuloMiNuevoCorteDePelo()
    }

    private var callbacks: Callbacks? = null

    private lateinit var misCortesDePeloButton: ImageButton
    private lateinit var miNuevoCorteDePeloButton: ImageButton


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "Fragmento $TAG creado")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_elijo_mi_peinado_pantalla_principal, container, false)

        misCortesDePeloButton = view.findViewById(R.id.elijoMiPeinadoPrincipal_misCortesDePeloButton)
        miNuevoCorteDePeloButton = view.findViewById(R.id.elijoMiPeinadoPrincipal_miNuevoCorteDePeloButton)

        return view
    }

    override fun onStart() {
        super.onStart()

        misCortesDePeloButton.setOnClickListener {
            Log.d(TAG, "Pulsado boton mis cortes de pelo")
            callbacks?.elijoMiPeinadoMontarModuloMisCortesDePelo()
        }

        miNuevoCorteDePeloButton.setOnClickListener {
            Log.d(TAG, "Pulsado boton mi nuevo corte de pelo")
            callbacks?.elijoMiPeinadoMontarModuloMiNuevoCorteDePelo()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Destruyendo fragmento Elijo mi peinado menu principal")
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }
}