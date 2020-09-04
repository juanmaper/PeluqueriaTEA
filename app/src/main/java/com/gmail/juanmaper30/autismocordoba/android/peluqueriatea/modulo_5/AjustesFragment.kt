package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.modulo_5

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.R

private const val TAG = "AjustesFragment"
private const val ARG_OPCION_CHICO_ELEGIDA = "opcion_chico_elegida"
private const val CONFIRMACION_BORRAR_PERSONAJES = "confirmacion_borrar_personajes"
private const val REQUEST_CONFIRMACION_BORRAR_PERSONAJES = 0

class AjustesFragment : Fragment(),
    ConfirmacionBorrarPersonajesDialogFragment.Callbacks {

    // Interfaz para comunicarme con MainActivity y decirle que cambie la opcion de chico o chica
    interface Callbacks {
        fun ajustesCambiarOpcionChicoChica(chicoElegido: Boolean)
        fun ajustesReiniciarListaAvataresGuardados()
        fun ajustesMontarGestionCitas()
        fun ajustesFinalizado()
    }

    private var callbacks: Callbacks? = null

    private var opcionChicoElegida: Boolean = true

    private lateinit var sexoPersonajeSwitch: Switch
    private lateinit var borrarPersonajesButton: Button
    private lateinit var gestionCitasButton: Button

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

        /* En los fragmentos no se puede hacer un override a onBackPressed() directamente,
        asi que creo esta callback para sobreescribir el funcionamiento del back button y llamar
        a la callback que pone la pantalla en horizontal. Lo hago aqui porque si lo hiciese
        en destroy o detach si se produce un cambio de configuracion se destruiria el fragmento
        y se llamaria a la callback, poniendo ajustes en horizontal cuando se reconstruya
         */
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d(TAG, "Pulsado boton back en ajustes")
                    isEnabled = false
                    callbacks?.ajustesFinalizado()
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
        val view = inflater.inflate(R.layout.fragment_ajustes, container, false)

        sexoPersonajeSwitch = view.findViewById(R.id.ajustes_sexoPersonajeSwitch)
        borrarPersonajesButton = view.findViewById(R.id.ajustes_reiniciarPersonajesButton)
        gestionCitasButton = view.findViewById(R.id.ajustes_gestionCitasButton)

        sexoPersonajeSwitch.isChecked = opcionChicoElegida

        return view
    }

    override fun onStart() {
        super.onStart()

        sexoPersonajeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                callbacks?.ajustesCambiarOpcionChicoChica(true)
            } else {
                callbacks?.ajustesCambiarOpcionChicoChica(false)
            }
        }

        borrarPersonajesButton.setOnClickListener {
            ConfirmacionBorrarPersonajesDialogFragment()
                .apply {
                setTargetFragment(this@AjustesFragment,
                    REQUEST_CONFIRMACION_BORRAR_PERSONAJES
                )
                show(this@AjustesFragment.requireActivity().supportFragmentManager,
                    CONFIRMACION_BORRAR_PERSONAJES
                )
            }
        }

        gestionCitasButton.setOnClickListener {
            callbacks?.ajustesMontarGestionCitas()
        }

    }

    override fun confirmacionBorrarPersonajes() {
        callbacks?.ajustesReiniciarListaAvataresGuardados()
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
            return AjustesFragment()
                .apply {
                arguments = args
            }
        }
    }

}