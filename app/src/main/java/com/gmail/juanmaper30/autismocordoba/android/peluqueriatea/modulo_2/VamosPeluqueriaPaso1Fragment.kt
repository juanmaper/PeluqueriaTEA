package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.modulo_2

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.R

private const val TAG = "VamosPeluqueriaPaso1"

class VamosPeluqueriaPaso1Fragment : Fragment() {

    // Interfaz para comunicarme con MainActivity y decirle que monte el siguiente fragmento
    interface Callbacks {
        fun vamosPeluqueriaMontarSiguienteFragmento()
    }

    private var callbacks: Callbacks? = null

    private lateinit var atrasButton: ImageButton
    private lateinit var siguienteButton: ImageButton

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
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
        val view = inflater.inflate(R.layout.fragment_vamos_a_la_peluqueria_paso1, container, false)

        atrasButton = view.findViewById(R.id.vamosPeluqueria_atrasButton) as ImageButton
        siguienteButton = view.findViewById(R.id.vamosPeluqueria_siguienteButton) as ImageButton

        return view
    }

    override fun onStart() {
        super.onStart()

        atrasButton.setOnClickListener {
            Log.i(TAG, "Saliendo de fragmento VamosPeluqueria Paso 1")
            activity?.onBackPressed()
        }

        siguienteButton.setOnClickListener {
            callbacks?.vamosPeluqueriaMontarSiguienteFragmento()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Destruyendo fragmento VamosPeluqueria Paso 1")
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }
}