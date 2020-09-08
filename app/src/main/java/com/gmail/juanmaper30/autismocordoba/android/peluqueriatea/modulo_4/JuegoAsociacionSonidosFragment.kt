package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.modulo_4

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.R

private const val TAG = "JuegoSonidosFragment"

class JuegoAsociacionSonidosFragment : Fragment() {

    // Interfaz para comunicarme con MainActivity y decirle que monte la pantalla
    // de Correcto/Incorrecto
    interface Callbacks {
        fun juegoAsociacionSonidosPantallaResultado(acierto: Boolean, objeto: ObjetoSonidoPeluqueria,
                                                    finalDelJuego: Boolean)
    }

    private var callbacks: Callbacks? = null

    private lateinit var secadorImageButton: ImageButton
    private lateinit var maquinillaElectricaImageButton: ImageButton
    private lateinit var sprayImageButton: ImageButton
    private lateinit var tijerasImageButton: ImageButton
    private lateinit var altavozImageView: ImageView
    //private lateinit var casaImageButton: ImageButton
    //private lateinit var comentarioTextView: TextView

    // Me creo el viewmodel para guardar los reproductores asi como el indice
    private val juegoSonidosViewModel: JuegoAsociacionSonidosViewModel by lazy {
        ViewModelProvider(this).get(JuegoAsociacionSonidosViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Fragmento $TAG creado")
        juegoSonidosViewModel.inicializarReproductor(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_juego_asociacion_sonidos, container, false)

        secadorImageButton = view.findViewById(R.id.juegoAsociacionSonidos_secadorImageButton)
        maquinillaElectricaImageButton = view.findViewById(R.id.juegoAsociacionSonidos_maquinillaElectricaImageButton)
        sprayImageButton = view.findViewById(R.id.juegoAsociacionSonidos_sprayImageButton)
        tijerasImageButton = view.findViewById(R.id.juegoAsociacionSonidos_tijerasImageButton)
        //casaImageButton = view.findViewById(R.id.juegoAsociacionSonidos_casaImageButton)
        altavozImageView = view.findViewById(R.id.juegoAsociacionSonidos_altavozImageView)
        //comentarioTextView = view.findViewById(R.id.juegoAsociacionSonidos_comentarioTextView)

        if (juegoSonidosViewModel.altavozPulsado == 0) {
            ocultarBotones()
        }
        sombrearBotonesElegidos()
        //casaImageButton.visibility = View.INVISIBLE

        return view
    }

    override fun onStart() {
        super.onStart()

        altavozImageView.setOnClickListener {
            if (!juegoSonidosViewModel.hayAudioReproduciendose()) {
                juegoSonidosViewModel.reproducirSonidoPasoActual()

                juegoSonidosViewModel.incrementarNumeroPulsacionesAltavoz()
                //sombrearBotonesElegidos()
            }
        }

        /*
        casaImageButton.setOnLongClickListener {
            requireActivity().onBackPressed()
            true
        }
         */

        secadorImageButton.setOnClickListener {
            botonPulsado("Secador")
        }

        maquinillaElectricaImageButton.setOnClickListener {
            botonPulsado("Maquinilla")
        }

        sprayImageButton.setOnClickListener {
            botonPulsado("Spray")
        }

        tijerasImageButton.setOnClickListener {
            botonPulsado("Tijeras")
        }

        juegoSonidosViewModel.listaObjetos[0].reproductor?.setOnCompletionListener {
            revelarBotones()
        }

        juegoSonidosViewModel.listaObjetos[1].reproductor?.setOnCompletionListener {
            revelarBotones()
        }

        juegoSonidosViewModel.listaObjetos[2].reproductor?.setOnCompletionListener {
            revelarBotones()
        }

        juegoSonidosViewModel.listaObjetos[3].reproductor?.setOnCompletionListener {
            revelarBotones()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Destruyendo $TAG")
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    fun ocultarBotones() {
        secadorImageButton.visibility = View.INVISIBLE
        maquinillaElectricaImageButton.visibility = View.INVISIBLE
        sprayImageButton.visibility = View.INVISIBLE
        tijerasImageButton.visibility = View.INVISIBLE
    }

    fun revelarBotones() {
        secadorImageButton.visibility = View.VISIBLE
        maquinillaElectricaImageButton.visibility = View.VISIBLE
        sprayImageButton.visibility = View.VISIBLE
        tijerasImageButton.visibility = View.VISIBLE
    }

    fun botonPulsado(botonPulsado: String) {
        juegoSonidosViewModel.detenerAudio()
        // Se ha acertado el objeto, por lo que monto el modulo resultado con correcto y ensombrezco
        // el botón acertado
        if (botonPulsado == juegoSonidosViewModel.nombreObjetoActual) {
            callbacks?.juegoAsociacionSonidosPantallaResultado(true, juegoSonidosViewModel.objetoActual,
                juegoSonidosViewModel.indice == 3)
            juegoSonidosViewModel.incrementarIndice()
            juegoSonidosViewModel.reiniciarNumeroPulsacionesAltavoz()
            sombrearBotonesElegidos()
            ocultarBotones()
        }
        // No se ha acertado
        else {
            callbacks?.juegoAsociacionSonidosPantallaResultado(false, juegoSonidosViewModel.objetoActual,
                false)
        }
    }

    fun sombrearBotonesElegidos() {
        // Sombreo y desactivo el altavoz solo si ha sido pulsado dos veces
        /*
        if (juegoSonidosViewModel.altavozPulsado >= 2) {
            altavozImageView.isEnabled = false
            altavozImageView.setColorFilter(Color.argb(120, 0, 0, 0))
            comentarioTextView.text = resources.getText(R.string.juegoAsociacionSonidos_eleccionTextView)
        } else {
            altavozImageView.isEnabled = true
            altavozImageView.clearColorFilter()
            comentarioTextView.text = resources.getText(R.string.juegoAsociacionSonidos_comentarioTextView)
        } */

        // Sombreo los botones elegidos
        for (indice in 0 until juegoSonidosViewModel.indice) {
            when (juegoSonidosViewModel.listaObjetos[indice].nombreObjeto) {
                "Secador" -> {
                    secadorImageButton.setColorFilter(Color.argb(150, 0, 0, 0))
                    secadorImageButton.setBackgroundResource(R.drawable.button_pantalla_principal_normal_oscuro)
                    secadorImageButton.isEnabled = false
                }
                "Maquinilla" -> {
                    maquinillaElectricaImageButton.setColorFilter(Color.argb(150, 0, 0, 0))
                    maquinillaElectricaImageButton.setBackgroundResource(R.drawable.button_pantalla_principal_normal_oscuro)
                    maquinillaElectricaImageButton.isEnabled = false
                }
                "Spray" -> {
                    sprayImageButton.setColorFilter(Color.argb(150, 0, 0, 0))
                    sprayImageButton.setBackgroundResource(R.drawable.button_pantalla_principal_normal_oscuro)
                    sprayImageButton.isEnabled = false
                }
                "Tijeras" -> {
                    tijerasImageButton.setColorFilter(Color.argb(150, 0, 0, 0))
                    tijerasImageButton.setBackgroundResource(R.drawable.button_pantalla_principal_normal_oscuro)
                    tijerasImageButton.isEnabled = false
                }
            }
            Log.d(TAG, "Sombreado objeto en indice: $indice")
        }
    }

}