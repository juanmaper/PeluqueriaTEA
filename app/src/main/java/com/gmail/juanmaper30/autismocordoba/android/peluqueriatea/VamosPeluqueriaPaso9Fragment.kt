package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

private const val TAG = "VamosPeluqueriaPaso9"
private const val ARG_OPCION_CHICO_ELEGIDA = "opcion_chico_elegida"
private const val SHARED_PREFERENCES_CITA_LONG = "cita_en_forma_long_recordatorio_modulo_2"

class VamosPeluqueriaPaso9Fragment : Fragment() {

    // Interfaz para comunicarme con MainActivity y decirle que monte el siguiente fragmento
    interface Callbacks {
        fun vamosPeluqueriaMontarSiguienteFragmento()
        fun vamosPeluqueriaDecrementarIndiceCallback()
    }

    private var callbacks: Callbacks? = null

    private var opcionChicoElegida: Boolean = true
    private var citaFormaLong: Long = 0
    private var citaRecordatorioTexto: String = ""
    private val fechaCitaActual: Date = Date()
    private var hayQueRecordarLaCita: Boolean = false

    private lateinit var atrasButton: ImageButton
    private lateinit var siguienteButton: ImageButton
    private lateinit var pictogramaChicoChicaImageView: ImageView
    private lateinit var pasoTextView: TextView
    private lateinit var recordatorioTextView: TextView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* Aqui obtengo el dato tipo boolean que le paso a newInstance desde MainActivity para
        saber si he de mostrar un chico o una chica */
        opcionChicoElegida = arguments?.getSerializable(ARG_OPCION_CHICO_ELEGIDA) as Boolean
        Log.i(TAG, "Fragmento $TAG creado con opcion mostrar chico = $opcionChicoElegida")

        val fechaAntiguaPorDefecto = Date()
        fechaAntiguaPorDefecto.time -= 100000

        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        citaFormaLong = sharedPref.getLong(
            SHARED_PREFERENCES_CITA_LONG, fechaAntiguaPorDefecto.time)

        fechaCitaActual.time = citaFormaLong

        Log.d(TAG, "Long recuperado: $citaFormaLong")
        Log.d(TAG, "Fecha cita: ${fechaCitaActual.toString()}")


        hayQueRecordarLaCita = fechaCitaActual > Date()
        Log.d(TAG, "Hay que recordar la cita: $hayQueRecordarLaCita")

        /* En los fragmentos no se puede hacer un override a onBackPressed() directamente,
        asi que creo esta callback para sobreescribir el funcionamiento del back button. Le digo
        a MainActivity que decremente el indice del viewmodel.
         */
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d(TAG, "Pulsado boton back")
                    callbacks?.vamosPeluqueriaDecrementarIndiceCallback()
                    isEnabled = false
                    requireActivity().onBackPressed()
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vamos_a_la_peluqueria_paso9, container, false)

        atrasButton = view.findViewById(R.id.vamosPeluqueria_atrasButton) as ImageButton
        siguienteButton = view.findViewById(R.id.vamosPeluqueria_siguienteButton) as ImageButton
        pictogramaChicoChicaImageView = view.findViewById(R.id.vamosPeluqueria_pictogramaChicoSentadoImageView) as ImageView
        pasoTextView = view.findViewById(R.id.vamosPeluqueria_paso9TextView) as TextView
        recordatorioTextView = view.findViewById(R.id.recordatorioCitaTextView) as TextView

        if (opcionChicoElegida) {
            pictogramaChicoChicaImageView.setImageResource(R.drawable.ic_chico_tocandose_cabeza)
            pasoTextView.setText(R.string.vamosPeluqueria_chico_paso_9)
        } else {
            pictogramaChicoChicaImageView.setImageResource(R.drawable.ic_chica_tocandose_cabeza)
            pasoTextView.setText(R.string.vamosPeluqueria_chica_paso_9)
        }

        if (hayQueRecordarLaCita) {
            recordatorioTextView.text = SimpleDateFormat("'Tu cita es el' dd 'de' MMMM 'a las' HH:mm")
                .format(fechaCitaActual)
        } else {
            recordatorioTextView.visibility = View.INVISIBLE
        }

        return view
    }


    override fun onStart() {
        super.onStart()

        atrasButton.setOnClickListener {
            Log.i(TAG, "Saliendo de fragmento VamosPeluqueria Paso 9")
            activity?.onBackPressed()
        }

        siguienteButton.setOnClickListener {
            callbacks?.vamosPeluqueriaMontarSiguienteFragmento()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Destruyendo fragmento VamosPeluqueria Paso 9")
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    /* Companion object, se puede llamar a la funcion como si fuese un metodo */
    companion object {
        /* Crea instancias de VamosPeluqueriaPaso9Fragment, recogiendo el valor que se le haya
        pasado como argumento, es decir, el boolean que indica si he de mostrar un chico o una chica
         */
        fun newInstance(opcionChicoElegida: Boolean): VamosPeluqueriaPaso9Fragment {
            val args = Bundle().apply {
                putSerializable(ARG_OPCION_CHICO_ELEGIDA, opcionChicoElegida)
            }
            return VamosPeluqueriaPaso9Fragment().apply {
                arguments = args
            }
        }
    }
}