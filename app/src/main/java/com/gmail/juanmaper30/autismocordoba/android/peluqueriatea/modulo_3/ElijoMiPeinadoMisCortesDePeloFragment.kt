package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.modulo_3

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.modelos.Avatar
import com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.R
import java.io.Serializable

private const val TAG = "ElijoMiPeinadoMisCortes"
private const val ARG_LISTA_AVATARES = "lista_de_avatares"
private const val ARG_NUMERO_AVATARES = "numero_de_avatares"

class ElijoMiPeinadoMisCortesDePeloFragment : Fragment() {

    interface Callbacks {
        fun elijoMiPeinadoMisCortesDePeloTerminado()
    }

    private var callbacks: Callbacks? = null

    private lateinit var atrasButton: ImageButton
    private lateinit var siguienteButton: ImageButton
    private lateinit var avatar1ImageView: ImageView
    private lateinit var avatar2ImageView: ImageView
    private lateinit var avatar3ImageView: ImageView

    private var listaAvatares = mutableListOf<Int>(0, 0, 0)
    private var numeroAvatares = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("UNCHECKED_CAST")
        /* Kotlin siempre pone un warning al hacer un casting a una lista, ya que tambien funcionaria
        si la lista fuese de otro tipo. En este caso, suprimo el warning porque sé que el tipo
        de la lista es entero
         */
        listaAvatares = arguments?.getSerializable(ARG_LISTA_AVATARES) as MutableList<Int>
        numeroAvatares = arguments?.getSerializable(ARG_NUMERO_AVATARES) as Int

        Log.d(TAG, "Numero de avatares recibido en Mis Cortes de Pelo: $numeroAvatares")
        Log.d(
            TAG, "Lista recibida en Mis Cortes de Pelo-> [0]: ${listaAvatares[0]}, " +
                "[1]: ${listaAvatares[1]}, [2]: ${listaAvatares[2]}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_elijo_mi_peinado_mis_cortes_de_pelo, container, false)

        atrasButton = view.findViewById(R.id.elijoMiPeinadoMisCortesDePelo_atrasButton)
        siguienteButton = view.findViewById(R.id.elijoMiPeinadoMisCortesDePelo_siguienteButton)
        avatar1ImageView = view.findViewById(R.id.avatar1_ImageView)
        avatar2ImageView = view.findViewById(R.id.avatar2_ImageView)
        avatar3ImageView = view.findViewById(R.id.avatar3_ImageView)

        dibujarAvataresAlmacenados()

        return view
    }

    override fun onStart() {
        super.onStart()

        /* El boton de atras llama al boton de retroceso, el siguiente usa la callback para decirle
        a MainActivity que termine el módulo y quite todos los fragmentos para quedarme en la
        pantalla principal
         */
        atrasButton.setOnClickListener {
            Log.i(TAG, "Saliendo de fragmento Elijo Mi Peinado Mis Cortes de pelo")
            activity?.onBackPressed()
        }

        siguienteButton.setOnClickListener {
            callbacks?.elijoMiPeinadoMisCortesDePeloTerminado()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Destruyendo fragmento Elijo mi peinado Mis Cortes de Pelo")
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    fun dibujarAvataresAlmacenados() {
        when (numeroAvatares) {
            1 -> avatar2ImageView.setImageResource(
                Avatar(
                    listaAvatares[0]
                ).idRecursoAsociado())
            2 ->{
                avatar1ImageView.setImageResource(
                    Avatar(
                        listaAvatares[0]
                    ).idRecursoAsociado())
                avatar3ImageView.setImageResource(
                    Avatar(
                        listaAvatares[1]
                    ).idRecursoAsociado())
            }
            3 -> {
                avatar1ImageView.setImageResource(
                    Avatar(
                        listaAvatares[0]
                    ).idRecursoAsociado())
                avatar2ImageView.setImageResource(
                    Avatar(
                        listaAvatares[1]
                    ).idRecursoAsociado())
                avatar3ImageView.setImageResource(
                    Avatar(
                        listaAvatares[2]
                    ).idRecursoAsociado())
            }
        }
    }


    /* Companion object, se puede llamar a la funcion como si fuese un metodo */
    companion object {
        /* Crea instancias de ElijoMiPeinadoMisCortesDePelo. Le paso la lista de avatares guardados
        y el numero de avatares guardados
         */
        fun newInstance(listaAvatares: MutableList<Int>, numeroAvatares: Int)
                : ElijoMiPeinadoMisCortesDePeloFragment {
            val args = Bundle().apply {
                putSerializable(ARG_LISTA_AVATARES, listaAvatares as Serializable)
                putSerializable(ARG_NUMERO_AVATARES, numeroAvatares)
            }
            return ElijoMiPeinadoMisCortesDePeloFragment()
                .apply {
                arguments = args
            }
        }
    }
}