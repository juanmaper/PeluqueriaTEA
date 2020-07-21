package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.DateFormat
import java.util.*

private const val TAG = "GestionCitasFragment"

class AjustesGestionCitasFragment : Fragment() {

    interface Callbacks {
        fun loquesea()
    }

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

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gestionCitasViewModel.listaCitasPeluqueriaLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { citasPeluqueria ->
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

    fun actualizarInterfaz(citasPeluqueria: List<CitaPeluqueria>) {
        adapter = CitaPeluqueriaAdapter(citasPeluqueria)
        citasPeluqueriaRecyclerView.adapter = adapter
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

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(citaPeluqueria: CitaPeluqueria) {
            this.citaPeluqueria = citaPeluqueria
            fechaTextView.text = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
                .format(this.citaPeluqueria.fecha)
            comentarioTextView.text = this.citaPeluqueria.comentario
        }

        override fun onClick(v: View?) {
            Toast.makeText(context, "Pulsada cita: ${citaPeluqueria.id}", Toast.LENGTH_SHORT).show()
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