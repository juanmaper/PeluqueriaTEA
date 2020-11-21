package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.modulo_6

import android.content.Context
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.R


private const val TAG = "CreditosFragment"

class CreditosFragment : Fragment() {

    interface Callbacks {
        fun creditosFinalizado()
    }

    private var callbacks: Callbacks? = null

    private lateinit var desarrolladorTextView: TextView
    private lateinit var coordinadorTextView: TextView
    private lateinit var equipoInvestigadorTextView: TextView
    private lateinit var apoyoTextView: TextView
    private lateinit var recursosTextView: TextView


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
        val view = inflater.inflate(R.layout.fragment_creditos, container, false)

        desarrolladorTextView = view.findViewById(R.id.creditos_desarrolladorTextView)
        coordinadorTextView = view.findViewById(R.id.creditos_coordinadoresTextView)
        equipoInvestigadorTextView = view.findViewById(R.id.creditos_equipoInvestigadorTextView)
        apoyoTextView = view.findViewById(R.id.creditos_apoyoTextView)
        recursosTextView = view.findViewById(R.id.creditos_recursosTextView)

        desarrolladorTextView.movementMethod = LinkMovementMethod.getInstance()
        coordinadorTextView.movementMethod = LinkMovementMethod.getInstance()
        equipoInvestigadorTextView.movementMethod = LinkMovementMethod.getInstance()
        apoyoTextView.movementMethod = LinkMovementMethod.getInstance()
        recursosTextView.movementMethod = LinkMovementMethod.getInstance()

        return view
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