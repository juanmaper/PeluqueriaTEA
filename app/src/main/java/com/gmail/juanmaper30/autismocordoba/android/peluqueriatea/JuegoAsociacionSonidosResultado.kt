package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.io.Serializable

private const val TAG = "JuegoResultadoFragment"
private const val ARG_ACIERTO = "objeto_acertado"
private const val ARG_OBJETO_PELUQUERIA = "objeto_peluqueria"
private const val ARG_FINAL_DEL_JUEGO = "final_del_juego"

class JuegoAsociacionSonidosResultado : Fragment() {

    // Interfaz para comunicarme con MainActivity y decirle que saque los fragmentos del modulo
    interface Callbacks {
        fun juegoAsociacionSonidosTerminado()
    }

    private var callbacks: Callbacks? = null

    private var acierto = false
    private var finalDelJuego = false
    private lateinit var objetoPeluqueria: ObjetoSonidoPeluqueria
    private lateinit var objetoImageView: ImageView
    private lateinit var comentarioTextView: TextView
    private lateinit var comentarioAvanceTextView: TextView
    private lateinit var pantallaEnteraButton: Button

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* Aqui obtengo el dato tipo boolean que le paso a newInstance desde MainActivity para
        saber si se ha acertado el objeto o no, y ademas obtengo el dato tipo ObjetoSonidoPeluqueria
         con el nombre del objeto en si junto a su sonido*/
        acierto = arguments?.getSerializable(ARG_ACIERTO) as Boolean
        objetoPeluqueria = arguments?.getSerializable(ARG_OBJETO_PELUQUERIA) as ObjetoSonidoPeluqueria
        finalDelJuego = arguments?.getSerializable(ARG_FINAL_DEL_JUEGO) as Boolean
        Log.i(TAG, "Fragmento $TAG creado con acierto = $acierto y nombre objeto = " +
                "${objetoPeluqueria.nombreObjeto} y final del juego = $finalDelJuego"
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_juego_asociacion_sonidos_resultado, container, false)

        objetoImageView = view.findViewById(R.id.juegoAsociacionSonidos_ResultadoImageView)
        comentarioTextView = view.findViewById(R.id.juegoAsociacionSonidos_ResultadoTextView)
        comentarioAvanceTextView = view.findViewById(R.id.juegoAsociacionSonidos_ResultadoComentarioAvanceTextView)
        pantallaEnteraButton = view.findViewById(R.id.juegoAsociacionSonidos_ResultadoPantallaEnteraButton)

        if (acierto) {
            comentarioTextView.text = resources.getText(R.string.juegoAsociacionSonidos_ResultadoCorrectoTextView)
            comentarioAvanceTextView.text = resources.getText(
                R.string.juegoAsociacionSonidos_ResultadoComentarioSiguienteObjetoTextView)
        } else {
            comentarioTextView.text = resources.getText(R.string.juegoAsociacionSonidos_ResultadoIncorrectoTextView)
            comentarioTextView.setTextColor(resources.getColor(R.color.rojo))
            comentarioAvanceTextView.text = resources.getText(
                R.string.juegoAsociacionSonidos_ResultadoComentarioAnteriorObjetoTextView)
        }

        when (objetoPeluqueria.nombreObjeto) {
            "Secador" -> objetoImageView.setImageResource(R.drawable.ic_secador)
            "Maquinilla" -> objetoImageView.setImageResource(R.drawable.ic_maquinilla_electrica)
            "Spray" -> objetoImageView.setImageResource(R.drawable.ic_spray)
            "Tijeras" -> objetoImageView.setImageResource(R.drawable.ic_tijeras2)
        }

        if (objetoPeluqueria.reproductor?.isPlaying!!) {
            Log.d(TAG, "Resultado y suena audio")
        }

        objetoPeluqueria.reproductor?.start()

        return view
    }

    override fun onStart() {
        super.onStart()

        pantallaEnteraButton.setOnClickListener {
            if (objetoPeluqueria.reproductor?.isPlaying!!) {
                objetoPeluqueria.reproductor?.pause()

                if (!acierto) {
                    objetoPeluqueria.reproductor?.seekTo(0)
                }
            }
            if (finalDelJuego) {
                callbacks?.juegoAsociacionSonidosTerminado()
            }
            else {
                requireActivity().onBackPressed()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Destruyendo fragmento $TAG")
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }


    /* Companion object, se puede llamar a la funcion como si fuese un metodo */
    companion object {
        /* Crea instancias de JuegoAsociacionSonidosResultado, recogiendo el valor que se le haya
        pasado como argumento, es decir, el boolean que indica si se ha acertado el objeto o no y
        el objeto en sí, conteniendo el nombre de este y su sonido
         */
        fun newInstance(acierto: Boolean, objeto: ObjetoSonidoPeluqueria, finalDelJuego: Boolean):
                JuegoAsociacionSonidosResultado {
            val args = Bundle().apply {
                putSerializable(ARG_ACIERTO, acierto)
                putSerializable(ARG_OBJETO_PELUQUERIA, objeto as Serializable)
                putSerializable(ARG_FINAL_DEL_JUEGO, finalDelJuego)
            }
            return JuegoAsociacionSonidosResultado().apply {
                arguments = args
            }
        }
    }
}