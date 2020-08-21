package com.gmail.juanmaper30.autismocordoba.android.peluqueriatea

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.lifecycle.Observer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.io.Serializable
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "AjustesEditarCita"
private const val ARG_CITA_PELUQUERIA = "cita_peluqueria"
private const val FECHA_DIALOG = "fechaDialog"
private const val REQUEST_FECHA = 0
private const val HORA_DIALOG = "horaDialog"
private const val REQUEST_HORA = 1
private const val CONFIRMACION_BORRAR_CITA_DIALOG = "confirmacionBorrarCitaDialog"
private const val REQUEST_BORRAR_CITA = 2
private const val DESCARTAR_CAMBIOS_DIALOG = "descartarCambios"
private const val REQUEST_DESCARTAR_CAMBIOS = 3

class AjustesEditarCitaFragment : Fragment(), DatePickerFragment.Callbacks,
    TimePickerFragment.Callbacks, ConfirmacionBorrarCitaDialogFragment.Callbacks,
    ConfirmacionDescartarCambiosDialogFragment.Callbacks{

    interface Callbacks {
        fun ajustesEdicionCitasFinalizado()
    }

    private lateinit var fechaTextView: TextView
    private lateinit var horaTextView: TextView
    private lateinit var comentarioEditText: EditText
    private lateinit var fechaButton: Button
    private lateinit var horaButton: Button
    private lateinit var borrarButton: Button
    private var botonGuardarPulsado: Boolean = false
    private var confirmacionDescartarCambios: Boolean = false

    private var callbacks: Callbacks? = null

    // Me creo el viewmodel para guardar los datos y hacer las consultas a la base de datos
    private val editarCitaViewModel: AjustesEditarCitaViewModel by lazy {
        ViewModelProvider(this).get(AjustesEditarCitaViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Fragmento $TAG creado")
        val citaPeluqueriaPasada: CitaPeluqueria = arguments?.getSerializable(ARG_CITA_PELUQUERIA)
                as CitaPeluqueria
        editarCitaViewModel.cargarCitaPeluqueria(citaPeluqueriaPasada)
        setHasOptionsMenu(true)
        /* En los fragmentos no se puede hacer un override a onBackPressed() directamente,
        asi que creo esta callback para sobreescribir el funcionamiento del back button.
         */
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d(TAG, "Pulsado boton back")

                    if (!editarCitaViewModel.algoHaSidoEditado || botonGuardarPulsado || confirmacionDescartarCambios) {
                        isEnabled = false
                        callbacks?.ajustesEdicionCitasFinalizado()
                        requireActivity().onBackPressed()
                    } else {
                        ConfirmacionDescartarCambiosDialogFragment().apply {
                            setTargetFragment(this@AjustesEditarCitaFragment, REQUEST_DESCARTAR_CAMBIOS)
                            show(this@AjustesEditarCitaFragment.requireActivity().supportFragmentManager,
                                DESCARTAR_CAMBIOS_DIALOG)
                        }
                    }
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ajustes_editar_cita, container, false)

        fechaTextView = view.findViewById(R.id.ajustes_citaFechaElegidaTextView)
        horaTextView = view.findViewById(R.id.ajustes_citaHoraElegidaTextView)
        comentarioEditText = view.findViewById(R.id.ajustes_citaComentarioEditText)

        fechaButton = view.findViewById(R.id.ajustes_citaEleccionFechaButton)
        horaButton = view.findViewById(R.id.ajustes_citaEleccionHoraButton)
        borrarButton = view.findViewById(R.id.ajustes_citaBorrarButton)

        //borrarButton.setBackgroundColor(resources.getColor(R.color.rojo))

        actualizarInterfaz()

        return view
    }


    override fun onStart() {
        super.onStart()

        fechaButton.setOnClickListener {
            DatePickerFragment.newInstance(editarCitaViewModel.citaPeluqueria.fecha).apply {
                setTargetFragment(this@AjustesEditarCitaFragment, REQUEST_FECHA)
                show(this@AjustesEditarCitaFragment.requireActivity().supportFragmentManager,
                    FECHA_DIALOG)
            }
        }

        horaButton.setOnClickListener {
            TimePickerFragment.newInstance(editarCitaViewModel.citaPeluqueria.fecha).apply {
                setTargetFragment(this@AjustesEditarCitaFragment, REQUEST_HORA)
                show(this@AjustesEditarCitaFragment.requireActivity().supportFragmentManager,
                    HORA_DIALOG)
            }
        }


        comentarioEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                editarCitaViewModel.citaPeluqueria.comentario = s.toString()

                if (comentarioEditText.hasFocus()) {
                    editarCitaViewModel.algoHaSidoEditado = true
                }
            }

            override fun afterTextChanged(editable: Editable?) {
            }
        })


        borrarButton.setOnClickListener {
            ConfirmacionBorrarCitaDialogFragment().apply {
                setTargetFragment(this@AjustesEditarCitaFragment, REQUEST_BORRAR_CITA)
                show(this@AjustesEditarCitaFragment.requireActivity().supportFragmentManager,
                    CONFIRMACION_BORRAR_CITA_DIALOG)
            }
        }

    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_ajustes_nueva_editar_cita, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.guardar_cita -> {
                if (editarCitaViewModel.citaPeluqueria.fecha <= Date() && !editarCitaViewModel.citaAntigua) {
                    Toast.makeText(requireContext(), "La hora ha de ser posterior a la actual",
                        Toast.LENGTH_SHORT).show()
                } else {
                    editarCitaViewModel.actualizarCitaPeluqueria(editarCitaViewModel.citaPeluqueria)
                    Toast.makeText(requireActivity(), "Cita editada correctamente", Toast.LENGTH_SHORT)
                        .show()
                    botonGuardarPulsado = true
                    requireActivity().onBackPressed()
                }
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun actualizarInterfaz() {
        fechaTextView.text = SimpleDateFormat("dd-MM-yyyy")
            .format(editarCitaViewModel.citaPeluqueria.fecha)

        horaTextView.text = DateFormat.getTimeInstance(DateFormat.SHORT)
            .format(editarCitaViewModel.citaPeluqueria.fecha)
        
        comentarioEditText.setText(editarCitaViewModel.citaPeluqueria.comentario)

        /*
         Solo hay una cita actual, toda cita mas antigua que el momento presente son antiguas y por
         tanto, deshabilito los botones de fecha y hora, puesto que no dejo editarlas si ya
         han pasado
        */

        if (editarCitaViewModel.citaPeluqueria.fecha <= Date()) {
            fechaButton.isEnabled = false
            fechaButton.alpha = 0.5F

            horaButton.isEnabled = false
            horaButton.alpha = 0.5F

            editarCitaViewModel.citaAntigua = true
        }
    }

    override fun enFechaSeleccionada(fecha: Date) {
        editarCitaViewModel.citaPeluqueria.fecha = fecha
        actualizarInterfaz()
        editarCitaViewModel.algoHaSidoEditado = true
    }

    override fun enHoraSeleccionada(fecha: Date) {
        editarCitaViewModel.citaPeluqueria.fecha = fecha
        actualizarInterfaz()
        editarCitaViewModel.algoHaSidoEditado = true
    }

    override fun confirmacionBorrarCita() {
        editarCitaViewModel.borrarCitaPeluqueria(editarCitaViewModel.citaPeluqueria)
        Toast.makeText(requireActivity(), "Cita borrada correctamente", Toast.LENGTH_SHORT)
            .show()
        confirmacionDescartarCambios = true
        requireActivity().onBackPressed()
    }

    override fun confirmacionDescartarCambios() {
        callbacks?.ajustesEdicionCitasFinalizado()
        confirmacionDescartarCambios = true
        requireActivity().onBackPressed()
    }

    /* Companion object, se puede llamar a la funcion como si fuese un metodo */
    companion object {
        /* Crea instancias de AjustesCitaFragment, recogiendo el valor que se le haya
        pasado como argumento, es decir, la cita de peluqueria pulsada
         */
        fun newInstance(citaPeluqueria: CitaPeluqueria): AjustesEditarCitaFragment {
            val args = Bundle().apply {
                putSerializable(ARG_CITA_PELUQUERIA, citaPeluqueria as Serializable)
            }
            return AjustesEditarCitaFragment().apply {
                arguments = args
            }
        }
    }
}