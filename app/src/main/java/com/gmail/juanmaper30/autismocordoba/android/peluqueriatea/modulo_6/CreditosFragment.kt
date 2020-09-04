package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.modulo_6

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.R


private const val TAG = "CreditosFragment"

class CreditosFragment : Fragment() {

    interface Callbacks {
        fun creditosFinalizado()
    }

    private var callbacks: Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Fragmento $TAG creado")

        /* En los fragmentos no se puede hacer un override a onBackPressed() directamente,
        asi que creo esta callback para sobreescribir el funcionamiento del back button y llamar
        a la callback que pone la pantalla en horizontal. Lo hago aqui porque si lo hiciese
        en destroy o detach si se produce un cambio de configuracion se destruiria el fragmento
        y se llamaria a la callback, poniendo creditos en horizontal cuando se reconstruya
         */
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d(TAG, "Pulsado boton back en creditos")
                    isEnabled = false
                    callbacks?.creditosFinalizado()
                    requireActivity().onBackPressed()
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_creditos, container, false)
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Destruyendo fragmento Creditos")
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }
}