package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

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
import androidx.lifecycle.ViewModelProvider


private const val TAG = "ConsejosFragment"
private const val KEY_INDICE = "indiceActual"
private const val NUMERO_CONSEJOS = 7

class ConsejosFragment : Fragment() {

    private lateinit var atrasButton: ImageButton
    private lateinit var siguienteButton: ImageButton
    private lateinit var pictogramaImageView: ImageView
    private lateinit var consejoTextView: TextView

    private val consejosViewModel: ConsejosViewModel by lazy {
        ViewModelProvider(this).get(ConsejosViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* Fijo el numero de consejos en funcion de la constante definida en este fragmento.
         Antes miro si hay algo en el bundle, por si el OS ha destruido el proceso, si no, pongo
         el indice a 1 */
        val indiceConsejoActual = savedInstanceState?.getInt(KEY_INDICE, 1) ?: 1
        Log.i(TAG, "Valor cogido de savedInstanceState o defecto: $indiceConsejoActual")
        consejosViewModel.indiceActual = indiceConsejoActual

        consejosViewModel.numeroConsejos = NUMERO_CONSEJOS
        Log.i(TAG, "Fragmento consejos creado")

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d(TAG, "Pulsado boton back")

                    if (consejosViewModel.estoyEnPrimerConsejo) {
                        isEnabled = false
                        requireActivity().onBackPressed()
                    } else {
                        consejosViewModel.decrementarIndice()
                        actualizarPantalla()
                    }
                }
            }
        )
    }

    //Hago un inflate al xml de consejos
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_consejos, container, false)

        atrasButton = view.findViewById(R.id.consejos_atrasButton) as ImageButton
        siguienteButton = view.findViewById(R.id.consejos_siguienteButton) as ImageButton
        pictogramaImageView = view.findViewById(R.id.consejos_pictogramaImageView) as ImageView
        consejoTextView = view.findViewById(R.id.consejos_consejoTextView)

        actualizarPantalla()

        return view
    }


    override fun onStart() {
        super.onStart()

        atrasButton.setOnClickListener {
            // Si estoy en el primer consejo, entonces vuelvo a la pantalla principal.
            if (consejosViewModel.estoyEnPrimerConsejo) {
                Log.i(TAG, "Saliendo de fragmento consejos")
                activity?.onBackPressed()
            } else { // Si no, decremento el indice y muestro el consejo correspondiente
                consejosViewModel.decrementarIndice()
                actualizarPantalla()

                Log.i(TAG,"Valor indice: ${consejosViewModel.indiceConsejoActual}")
            }
        }

        siguienteButton.setOnClickListener {
            // Si estoy en el ultimo consejo, entonces vuelvo a la pantalla principal.
            if (consejosViewModel.estoyEnUltimoConsejo) {
                Log.i(TAG, "Saliendo de fragmento consejos por haber terminado")
                activity?.supportFragmentManager?.popBackStack();
            } else { // Si no, incremento el indice y muestro el consejo correspondiente
                consejosViewModel.incrementarIndice()
                actualizarPantalla()

                Log.i(TAG,"Valor indice: ${consejosViewModel.indiceConsejoActual}")
            }

        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "Guardando saveInstanceState de ConsejosFragment con indice: ${consejosViewModel.indiceConsejoActual}")
        savedInstanceState.putInt(KEY_INDICE, consejosViewModel.indiceConsejoActual)
    }


    private fun actualizarTextoConsejo() {
        val idTextoConsejoParaMostrar = activity!!.resources
            .getIdentifier("consejos_consejo_${consejosViewModel.indiceConsejoActual}",
                "string", activity?.packageName)
        val consejo = activity!!.resources.getString(idTextoConsejoParaMostrar)
        consejoTextView.text = consejo
    }

    private fun actualizarImagenConsejo() {
        val idImagenConsejoParaMostrar = activity!!.resources
            .getIdentifier("ic_consejo_${consejosViewModel.indiceConsejoActual}",
                "drawable", activity?.packageName)
        Log.i(TAG, "Identificador real: ${R.drawable.ic_consejo_1}, identificador cogido: $idImagenConsejoParaMostrar")
        pictogramaImageView.setImageResource(idImagenConsejoParaMostrar)
    }

    private fun actualizarPantalla() {
        actualizarTextoConsejo()
        actualizarImagenConsejo()
    }
}