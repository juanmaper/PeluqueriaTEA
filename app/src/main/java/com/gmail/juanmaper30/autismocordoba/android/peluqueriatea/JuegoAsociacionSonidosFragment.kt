package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment

private const val TAG = "JuegoSonidosFragment"

class JuegoAsociacionSonidosFragment : Fragment() {

    private lateinit var secadorImageButton: ImageButton
    private lateinit var maquinillaElectricaImageButton: ImageButton
    private lateinit var sprayImageButton: ImageButton
    private lateinit var tijerasImageButton: ImageButton
    private lateinit var altavozImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Fragmento $TAG creado")
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
        altavozImageView = view.findViewById(R.id.juegoAsociacionSonidos_altavozImageView)

        ocultarBotones()

        return view
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Destruyendo $TAG")
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
}