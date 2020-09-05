package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.modulo_5

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.CitaPeluqueria
import com.gmail.juanmaper30.autismocordoba.android.peluqueriatea.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "GestionCitasFragment"
private const val SUSTITUIR_CITA_ACTUAL_DIALOG = "sustituirCitaActual"
private const val REQUEST_SUSTITUIR_CITA_ACTUAL = 0

class AjustesGestionCitasFragment : Fragment(),
    ConfirmacionSustituirCitaActual.Callbacks {

    interface Callbacks {
        fun ajustesGestionCitasMontarModuloEditarCita(citaPeluqueria: CitaPeluqueria)
        fun ajustesGestionCitasMontarModuloNuevaCita(idCitaActual: UUID, hayCitaActual: Boolean)
        fun ajustesGestionCitasFinalizado()
    }


    private lateinit var fechaCitaActualTextView: TextView
    private lateinit var horaCitaActualTextView: TextView
    private lateinit var citasPeluqueriaRecyclerView: RecyclerView
    private var adapter: CitaPeluqueriaAdapter? = CitaPeluqueriaAdapter(emptyList())

    private var callbacks: Callbacks? = null

    // Me creo el viewmodel para guardar el indice, asi persiste esa informacion
    private val gestionCitasViewModel: AjustesGestionCitasViewModel by lazy {
        ViewModelProvider(this).get(AjustesGestionCitasViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Fragmento $TAG creado")
        setHasOptionsMenu(true)

        /* En los fragmentos no se puede hacer un override a onBackPressed() directamente,
        asi que creo esta callback para sobreescribir el funcionamiento del back button.
         */
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d(TAG, "Pulsado boton back")
                    isEnabled = false
                    callbacks?.ajustesGestionCitasFinalizado()
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
        val view = inflater.inflate(R.layout.fragment_ajustes_gestion_citas, container, false)

        citasPeluqueriaRecyclerView = view.findViewById(R.id.ajustes_gestionCitasRecyclerView) as RecyclerView
        citasPeluqueriaRecyclerView.layoutManager = LinearLayoutManager(context)
        citasPeluqueriaRecyclerView.adapter = adapter

        fechaCitaActualTextView = view.findViewById(R.id.ajustes_gestionCitasFechaTextView)
        horaCitaActualTextView = view.findViewById(R.id.ajustes_gestionCitasHoraTextView)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        gestionCitasViewModel.listaCitasPeluqueriaLiveData.observe(
            viewLifecycleOwner,
            Observer { citasPeluqueria ->
                citasPeluqueria?.let {
                    Log.d(TAG, "Numero de citas: ${citasPeluqueria.size}")
                    actualizarInterfaz(citasPeluqueria)
                }
            }
        )


    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Destruyendo fragmento Gestion de citas")
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_ajustes_gestion_citas, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nueva_cita -> {
                if (gestionCitasViewModel.hayCitaActual) {
                    ConfirmacionSustituirCitaActual()
                        .apply {
                        setTargetFragment(this@AjustesGestionCitasFragment,
                            REQUEST_SUSTITUIR_CITA_ACTUAL
                        )
                        show(this@AjustesGestionCitasFragment.requireActivity().supportFragmentManager,
                            SUSTITUIR_CITA_ACTUAL_DIALOG
                        )
                    }
                } else {
                    Log.d(TAG, "Boton pulsado")
                    callbacks?.ajustesGestionCitasMontarModuloNuevaCita(gestionCitasViewModel.citaActual.id,
                        gestionCitasViewModel.hayCitaActual)
                }
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }





    override fun confirmacionSustituirCita() {
        callbacks?.ajustesGestionCitasMontarModuloNuevaCita(gestionCitasViewModel.citaActual.id, gestionCitasViewModel.hayCitaActual)
    }

    fun actualizarInterfaz(citasPeluqueria: List<CitaPeluqueria>) {
        adapter = CitaPeluqueriaAdapter(citasPeluqueria)
        citasPeluqueriaRecyclerView.adapter = adapter
        escribirCitaActual(citasPeluqueria)
    }

    fun escribirCitaActual(citasPeluqueria: List<CitaPeluqueria>) {
        if (!hayCitaActualMetodo(citasPeluqueria)) {
            fechaCitaActualTextView.text = resources.getString(R.string.ajustes_gestionCitasNoHayCitas)
            horaCitaActualTextView.text = ""
        } else {
            fechaCitaActualTextView.text = SimpleDateFormat("dd-MM-yyyy")
                .format(citasPeluqueria[0].fecha)

            horaCitaActualTextView.text = DateFormat.getTimeInstance(DateFormat.SHORT)
                .format(citasPeluqueria[0].fecha)
        }
    }

    fun hayCitaActualMetodo(citasPeluqueria: List<CitaPeluqueria>): Boolean {
        if (citasPeluqueria.isEmpty()) {
            gestionCitasViewModel.hayCitaActual = false
            return false
        } else if (citasPeluqueria[0].fecha > Date()) {
            gestionCitasViewModel.hayCitaActual = true
            gestionCitasViewModel.citaActual = citasPeluqueria[0]
            return true
        }
        gestionCitasViewModel.hayCitaActual = false
        return false
    }

    companion object {
        fun newInstance(): AjustesGestionCitasFragment {
            return AjustesGestionCitasFragment()
        }
    }

    private inner class CitaPeluqueriaHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var citaPeluqueria: CitaPeluqueria

        private val fechaTextView: TextView = itemView.findViewById(R.id.citaPeluqueriaRecyclerTextView)
        private val comentarioTextView: TextView = itemView.findViewById(R.id.comentarioPeluqueriaRecyclerTextView)
        private val citaCardView: CardView = itemView.findViewById(R.id.citaPeluqueriaRecyclerCardView)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(citaPeluqueria: CitaPeluqueria) {
            this.citaPeluqueria = citaPeluqueria
            fechaTextView.text = SimpleDateFormat("dd-MM-yyyy HH:mm")
                .format(this.citaPeluqueria.fecha)
            if (this.citaPeluqueria.comentario == "") {
                comentarioTextView.text = resources.getString(R.string.ajustes_gestionCitasNoHayComentarios)
            } else {
                comentarioTextView.text = this.citaPeluqueria.comentario
            }


            if (citaPeluqueria.fecha > Date()) {
                Log.d(TAG, "Pintando de verde fecha actual con comentario: ${citaPeluqueria.comentario}")
                citaCardView.setCardBackgroundColor(resources.getColor(R.color.verde_menta))
            }
        }

        override fun onClick(v: View?) {
            val citaParaEditar: CitaPeluqueria = this.citaPeluqueria.copy()
            callbacks?.ajustesGestionCitasMontarModuloEditarCita(citaParaEditar)
        }
    }

    private inner class CitaPeluqueriaAdapter(var citasPeluqueria: List<CitaPeluqueria>):
            RecyclerView.Adapter<CitaPeluqueriaHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaPeluqueriaHolder {
            val view = layoutInflater.inflate(R.layout.item_lista_cita_peluqueria, parent, false)
            return CitaPeluqueriaHolder(view)
        }

        override fun getItemCount() = citasPeluqueria.size

        override fun onBindViewHolder(holder: CitaPeluqueriaHolder, position: Int) {
            val citaPeluqueria = citasPeluqueria[position]
            holder.bind(citaPeluqueria)
        }
    }
}